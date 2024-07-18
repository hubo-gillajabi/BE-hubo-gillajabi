package com.hubo.gillajabi.admin.application.dto.request;

import com.hubo.gillajabi.global.ImageUrlProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SubQuestCreateRequest(
        @NotBlank(message = "제목은 필수 입니다.")
        @Size(max = 50, message = "제목은 50자리 까지 허용됩니다.")
        String title,

        @NotBlank(message = "설명은 필수 입니다.")
        @Size(max = 400, message = "설명은 400자리 까지 허용됩니다.")
        String description,

        @NotNull(message = "메인 퀘스트 아이디는 필수 입니다.")
        Long mainQuestId,

        String imageUrl
) implements ImageUrlProvider {

    @Override
    public String getImageUrl() {
        return imageUrl;
    }
}
