package com.hubo.gillajabi.image.domain.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.hubo.gillajabi.image.application.dto.response.ImageUrlResponse;
import com.hubo.gillajabi.image.domain.constant.ImageType;
import com.hubo.gillajabi.image.domain.entity.ImageUploadUrl;
import com.hubo.gillajabi.image.infrastructure.config.AwsS3Config;
import com.hubo.gillajabi.image.infrastructure.exception.ImageException;
import com.hubo.gillajabi.image.infrastructure.exception.ImageExceptionCode;
import com.hubo.gillajabi.image.infrastructure.presistence.ImageUploadUrlRepository;
import com.hubo.gillajabi.image.infrastructure.util.ImageUrlBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private final AwsS3Config awsS3Config;

    private static final long MAX_FILE_SIZE = 3 * 1024 * 1024; // 3MB

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.cloudfront.url}")
    private String cloudFrontUrl;

    private final ImageUploadUrlRepository imageUploadUrlRepository;

    public ImageUrlResponse uploadImage(MultipartFile file) {
        checkImageType(file);
        checkFileSize(file);
        String uploadUrl = ImageUrlBuilder.build(cloudFrontUrl);
        uploadImageToS3(file, uploadUrl);


        ImageUploadUrl uploadUrlEntity = ImageUploadUrl.createByUrl(uploadUrl);
        imageUploadUrlRepository.save(uploadUrlEntity);

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

    private String getS3KeyFromUrl(String url) {
        return url.replace(cloudFrontUrl + "/", "");
    }

    public void deleteImage(String imageUrl) {
        ImageUploadUrl imageUploadUrl = imageUploadUrlRepository.findById(imageUrl)
                .orElseThrow(() -> new ImageException(ImageExceptionCode.IMAGE_DELETE_FAILED));

        String s3Key = getS3KeyFromUrl(imageUrl);
        awsS3Config.s3Client().deleteObject(bucketName, s3Key);
        imageUploadUrlRepository.delete(imageUploadUrl);
    }
}
