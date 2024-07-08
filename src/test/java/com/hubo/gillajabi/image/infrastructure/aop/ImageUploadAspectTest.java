package com.hubo.gillajabi.image.infrastructure.aop;

import com.hubo.gillajabi.global.ImageUrlProvider;
import com.hubo.gillajabi.global.ImageUrlsProvider;
import com.hubo.gillajabi.image.domain.service.ImageValidationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageUploadAspectTest {

    @Mock
    private ImageValidationService imageValidationService;

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    @InjectMocks
    private ImageUploadAspect imageUploadAspect;

    @BeforeEach
    void setUp() {
        imageUploadAspect = new ImageUploadAspect(imageValidationService);
    }

    @Test
    @DisplayName("단일 이미지 URL 검증")
    void validateSingleImageUrl() throws Throwable {
        // given
        ImageUrlProvider imageUrlProvider = mock(ImageUrlProvider.class);
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{imageUrlProvider});
        when(imageUrlProvider.getImageUrl()).thenReturn("http://example.com/image.jpg");

        // when
        imageUploadAspect.validateImageUploadUrls(proceedingJoinPoint);

        // then
        verify(imageValidationService, times(1)).validateAndDeleteImageUrl("http://example.com/image.jpg");
        verify(proceedingJoinPoint, times(1)).proceed();
    }

    @Test
    @DisplayName("다중 이미지 URL 검증")
    void validateMultipleImageUrls() throws Throwable {
        // given
        ImageUrlsProvider imageUrlsProvider = mock(ImageUrlsProvider.class);
        List<String> imageUrls = Arrays.asList("http://example.com/image1.jpg", "http://example.com/image2.jpg");
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{imageUrlsProvider});
        when(imageUrlsProvider.getImageUrls()).thenReturn(imageUrls);

        // when
        imageUploadAspect.validateImageUploadUrls(proceedingJoinPoint);

        // then
        verify(imageValidationService, times(1)).validateAndDeleteImageUrls(imageUrls);
        verify(proceedingJoinPoint, times(1)).proceed();
    }
}
