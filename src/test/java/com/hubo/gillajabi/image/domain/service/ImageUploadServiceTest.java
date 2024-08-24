package com.hubo.gillajabi.image.domain.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.hubo.gillajabi.image.application.dto.response.ImageUrlResponse;
import com.hubo.gillajabi.image.domain.entity.ImageUploadUrl;
import com.hubo.gillajabi.image.infrastructure.config.AwsS3Config;
import com.hubo.gillajabi.image.infrastructure.exception.ImageException;
import com.hubo.gillajabi.image.infrastructure.exception.ImageExceptionCode;
import com.hubo.gillajabi.image.infrastructure.presistence.ImageUploadUrlRepository;
import com.hubo.gillajabi.image.infrastructure.util.ImageUrlBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageUploadServiceTest {

    @Mock
    private AwsS3Config awsS3Config;

    @Mock
    private AmazonS3 amazonS3;

    @Mock
    private ImageUploadUrlRepository imageUploadUrlRepository;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ImageUploadService imageUploadService;

    // TODO: 테스트 코드 추가
    @Test
    @DisplayName("이미지 업로드 실패 - 이미지 크기 초과")
    void testUploadImage_FileSizeTooLarge() {
        // given
        when(multipartFile.getContentType()).thenReturn("image/jpeg");
        when(multipartFile.getSize()).thenReturn(1000 * 1024 * 1024L); // 1000MB

        // when & then
        ImageException exception = assertThrows(ImageException.class, () -> imageUploadService.uploadImage(multipartFile));
        assertEquals(ImageExceptionCode.IMAGE_SIZE_TOO_LARGE.getErrorCode(), exception.getErrorCode());
    }

    @Test
    @DisplayName("이미지 삭제 실패 - URL 존재하지 않음")
    void testDeleteImage_NotFound() {
        // given
        String imageUrl = "http://test.cloudfront.net/image.jpg";
        when(imageUploadUrlRepository.findById(imageUrl)).thenReturn(Optional.empty());

        // when & then
        ImageException exception = assertThrows(ImageException.class, () -> imageUploadService.deleteImage(imageUrl));
        assertEquals(ImageExceptionCode.IMAGE_DELETE_FAILED.getErrorCode(), exception.getErrorCode());
    }
}
