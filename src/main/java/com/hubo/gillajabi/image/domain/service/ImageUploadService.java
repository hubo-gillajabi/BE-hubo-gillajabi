package com.hubo.gillajabi.image.domain.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.hubo.gillajabi.image.application.dto.response.ImageUrlResponse;
import com.hubo.gillajabi.image.domain.constant.ImageType;
import com.hubo.gillajabi.image.domain.entity.ImageGpsInfo;
import com.hubo.gillajabi.image.domain.entity.ImageUploadUrl;
import com.hubo.gillajabi.image.infrastructure.config.AwsS3Config;
import com.hubo.gillajabi.image.infrastructure.exception.ImageException;
import com.hubo.gillajabi.image.infrastructure.exception.ImageExceptionCode;
import com.hubo.gillajabi.image.infrastructure.presistence.ImageGpsInfoRepository;
import com.hubo.gillajabi.image.infrastructure.presistence.ImageUploadUrlRepository;
import com.hubo.gillajabi.image.infrastructure.util.ImageUrlBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.Metadata;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUploadService {

    private final AwsS3Config awsS3Config;

    private final ImageGpsInfoRepository imageGpsInfoRepository;

    private static final long MAX_FILE_SIZE = 30 * 1024 * 1024; // 30MB

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.cloudfront.url}")
    private String cloudFrontUrl;

    private final ImageUploadUrlRepository imageUploadUrlRepository;

    public ImageUrlResponse uploadImage(MultipartFile file) {
        log.info("Uploading file: name={}, size={} bytes", file.getOriginalFilename(), file.getSize());

        checkImageType(file);
        checkFileSize(file);
        String uploadUrl = ImageUrlBuilder.build(cloudFrontUrl);
        uploadImageToS3(file, uploadUrl);


        ImageUploadUrl uploadUrlEntity = ImageUploadUrl.createByUrl(uploadUrl);
        imageUploadUrlRepository.save(uploadUrlEntity);

        // GPS 정보 추출
        try {
            extractAndSaveImageInfo(file, uploadUrl);
        } catch (Exception e) {
            log.error("이미지 정보 추출 실패 ", e);
        }

        return new ImageUrlResponse(uploadUrl);
    }

    private void checkImageType(MultipartFile file) {
        String contentType = file.getContentType();
        ImageType.validate(contentType);
    }

    private void checkFileSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ImageException(ImageExceptionCode.IMAGE_SIZE_TOO_LARGE);
        }
    }

    private void uploadImageToS3(MultipartFile file, String uploadUrl) {
        String s3Key = getS3KeyFromUrl(uploadUrl);
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            awsS3Config.s3Client().putObject(bucketName, s3Key, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new ImageException(ImageExceptionCode.IMAGE_UPLOAD_FAILED);
        }
    }

    private void extractAndSaveImageInfo(MultipartFile file, String uploadUrl) throws IOException, ImageProcessingException {
        Metadata metadata = ImageMetadataReader.readMetadata(file.getInputStream());

        GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
        if (gpsDirectory != null && gpsDirectory.getGeoLocation() != null) {
            BigDecimal latitude = BigDecimal.valueOf(gpsDirectory.getGeoLocation().getLatitude());
            BigDecimal longitude = BigDecimal.valueOf(gpsDirectory.getGeoLocation().getLongitude());

            LocalDateTime dateTime = getDateTimeFromExif(metadata);

            ImageGpsInfo gpsInfo = ImageGpsInfo.createByGpsInfo(uploadUrl, latitude, longitude, dateTime);
            imageGpsInfoRepository.save(gpsInfo);
        } else {
            log.info("No GPS information found for image: {}", uploadUrl);
        }
    }

    private LocalDateTime getDateTimeFromExif(Metadata metadata) {
        ExifIFD0Directory exifDirectory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        if (exifDirectory != null) {
            String dateTimeString = exifDirectory.getString(ExifIFD0Directory.TAG_DATETIME);
            if (dateTimeString != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
                return LocalDateTime.parse(dateTimeString, formatter);
            }
        }
        return LocalDateTime.now();
    }

    private String getS3KeyFromUrl(final String url) {
        return url.replace(cloudFrontUrl + "/", "");
    }

    public void deleteImage(final String imageUrl) {
        ImageUploadUrl imageUploadUrl = imageUploadUrlRepository.findById(imageUrl)
                .orElseThrow(() -> new ImageException(ImageExceptionCode.IMAGE_DELETE_FAILED));

        final String s3Key = getS3KeyFromUrl(imageUrl);
        awsS3Config.s3Client().deleteObject(bucketName, s3Key);

        imageUploadUrlRepository.delete(imageUploadUrl);

        // Redis에서 GPS 정보 삭제
        final String gpsKey = ImageGpsInfo.getGpsInfoKey(imageUrl);
        imageGpsInfoRepository.deleteById(gpsKey);
    }
}
