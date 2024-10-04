package com.hubo.gillajabi.image.infrastructure.aop;

import com.hubo.gillajabi.global.ImageUrlProvider;
import com.hubo.gillajabi.global.ImageUrlsProvider;
import com.hubo.gillajabi.image.domain.service.ImageValidationService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class ImageUploadAspect {

    private final ImageValidationService imageValidationService;

    @Around("@annotation(com.hubo.gillajabi.image.application.annotation.ImageUploader)")
    public Object validateImageUploadUrls(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        if (args != null && args.length > 0) {
            if (args[0] instanceof ImageUrlProvider) {
                ImageUrlProvider dto = (ImageUrlProvider) args[0];
                String imageUrl = dto.getImageUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    imageValidationService.validateImageUrl(imageUrl);
                }
            } else if (args[0] instanceof ImageUrlsProvider) {
                ImageUrlsProvider dto = (ImageUrlsProvider) args[0];
                List<String> imageUrls = dto.getImageUrls();
                if (imageUrls != null && !imageUrls.isEmpty()) {
                    imageValidationService.validateImageUrls(imageUrls);
                }
            }
        }

        return joinPoint.proceed();
    }

    @After("@annotation(com.hubo.gillajabi.image.application.annotation.ImageUploader)")
    public void afterImageUpload(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        if (args != null && args.length > 0) {
            if (args[0] instanceof ImageUrlProvider) {
                ImageUrlProvider dto = (ImageUrlProvider) args[0];
                String imageUrl = dto.getImageUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    imageValidationService.deleteImageUrl(imageUrl);
                }
            } else if (args[0] instanceof ImageUrlsProvider) {
                ImageUrlsProvider dto = (ImageUrlsProvider) args[0];
                List<String> imageUrls = dto.getImageUrls();
                if (imageUrls != null && !imageUrls.isEmpty()) {
                    imageValidationService.deleteImageUrls(imageUrls);
                }
            }
        }
    }
}