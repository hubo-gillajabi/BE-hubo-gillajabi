package com.hubo.gillajabi.image.domain.service;

import com.hubo.gillajabi.image.domain.entity.ImageUploadUrl;
import com.hubo.gillajabi.image.infrastructure.exception.ImageException;
import com.hubo.gillajabi.image.infrastructure.exception.ImageExceptionCode;
import com.hubo.gillajabi.image.infrastructure.presistence.ImageUploadUrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageValidationServiceTest {

    @Mock
    private ImageUploadUrlRepository imageUploadUrlRepository;

    @InjectMocks
    private ImageValidationService imageValidationService;

    @Test
    @DisplayName("단일 이미지 URL 검증 및 삭제 - 성공")
    void 단일_이미지_url_검증_및_삭제_성공() {
        // given
        String imageUrl = "http://example.com/image.jpg";
        ImageUploadUrl imageUploadUrl = new ImageUploadUrl(imageUrl, null);
        when(imageUploadUrlRepository.findById(imageUrl)).thenReturn(Optional.of(imageUploadUrl));

        // when
        imageValidationService.validateAndDeleteImageUrl(imageUrl);

        // then
        verify(imageUploadUrlRepository, times(1)).delete(imageUploadUrl);
    }

    @Test
    @DisplayName("단일 이미지 URL 검증 및 삭제 - 실패")
    void 단일_이미지_url_검증_및_실패() {
        // given
        String imageUrl = "http://example.com/image.jpg";
        when(imageUploadUrlRepository.findById(imageUrl)).thenReturn(Optional.empty());

        // when & then
        ImageException exception = assertThrows(ImageException.class, () -> {
            imageValidationService.validateAndDeleteImageUrl(imageUrl);
        });
        assertEquals(ImageExceptionCode.IMAGE_NOT_VALID.getErrorCode(), exception.getErrorCode());
    }

    @Test
    @DisplayName("여러 이미지 URL 검증 및 삭제 - 성공")
    void 여러_이미지_URL_검증_및_삭제_성공() {
        // given
        List<String> imageUrls = Arrays.asList("http://example.com/image1.jpg", "http://example.com/image2.jpg");
        ImageUploadUrl imageUploadUrl1 = new ImageUploadUrl(imageUrls.get(0), null);
        ImageUploadUrl imageUploadUrl2 = new ImageUploadUrl(imageUrls.get(1), null);

        when(imageUploadUrlRepository.findById(imageUrls.get(0))).thenReturn(Optional.of(imageUploadUrl1));
        when(imageUploadUrlRepository.findById(imageUrls.get(1))).thenReturn(Optional.of(imageUploadUrl2));

        // when
        imageValidationService.validateAndDeleteImageUrls(imageUrls);

        // then
        verify(imageUploadUrlRepository, times(1)).delete(imageUploadUrl1);
        verify(imageUploadUrlRepository, times(1)).delete(imageUploadUrl2);
    }

    @Test
    @DisplayName("여러 이미지 URL 검증 및 삭제 - 실패")
    void 여러_이미지_URL_검증_및_삭제_실패() {
        // given
        List<String> imageUrls = Arrays.asList("http://example.com/image1.jpg", "http://example.com/image2.jpg");
        when(imageUploadUrlRepository.findById(imageUrls.get(0))).thenReturn(Optional.empty());

        // when & then
        ImageException exception = assertThrows(ImageException.class, () -> {
            imageValidationService.validateAndDeleteImageUrls(imageUrls);
        });
        assertEquals(ImageExceptionCode.IMAGE_NOT_VALID.getErrorCode(), exception.getErrorCode());
    }
}
