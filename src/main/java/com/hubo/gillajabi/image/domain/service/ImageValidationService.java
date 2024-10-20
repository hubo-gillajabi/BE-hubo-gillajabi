package com.hubo.gillajabi.image.domain.service;

import com.hubo.gillajabi.image.domain.entity.ImageUploadUrl;
import com.hubo.gillajabi.image.infrastructure.exception.ImageException;
import com.hubo.gillajabi.image.infrastructure.exception.ImageExceptionCode;
import com.hubo.gillajabi.image.infrastructure.presistence.ImageUploadUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageValidationService {

    private final ImageUploadUrlRepository imageUploadUrlRepository;

    public void validateImageUrls(List<String> imageUrls) {
        for (String imageUrl : imageUrls) {
            validateImageUrl(imageUrl);
        }
    }

    public void validateImageUrl(String imageUrl) {
        imageUploadUrlRepository.findById(imageUrl).orElseThrow(
                () -> new ImageException(ImageExceptionCode.IMAGE_NOT_VALID));
    }

    public void deleteImageUrls(List<String> imageUrls) {
        for (String imageUrl : imageUrls) {
            deleteImageUrl(imageUrl);
        }
    }

    public void deleteImageUrl(String imageUrl) {
        imageUploadUrlRepository.deleteById(imageUrl);
    }

    //        imageUploadUrlRepository.delete(imageUploadUrl);
}
