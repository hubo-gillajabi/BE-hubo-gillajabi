package com.hubo.gillajabi.admin.application.dto.request;

import com.hubo.gillajabi.global.ImageUrlProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MainQuestCreateRequest(
        @NotBlank(message = "제목은 필수 입니다.")
        @Size(max = 50, message = "제목은 50자리 까지 허용됩니다.")
        String title,

        @NotBlank(message = "설명은 필수 입니다.")
        @Size(max = 400, message = "설명은 400자리 까지 허용됩니다.")
        String description,

        Long cityId,

        Long courseThemeId,

        Long courseId,

        String imageUrl
) implements ImageUrlProvider {
    @Override
    public String getImageUrl() {
        return imageUrl;
    }
}
