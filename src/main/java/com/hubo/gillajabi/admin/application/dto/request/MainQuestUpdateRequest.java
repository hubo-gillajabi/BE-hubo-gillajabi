package com.hubo.gillajabi.admin.application.dto.request;

import com.hubo.gillajabi.global.ImageUrlProvider;
import com.hubo.gillajabi.global.common.validation.EnumValid;
import com.hubo.gillajabi.global.type.StatusType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Setter;

public record MainQuestUpdateRequest(
        @Size(max = 50, message = "제목은 50자리 까지 허용됩니다.")
        String title,

        @Size(max = 400, message = "설명은 400자리 까지 허용됩니다.")
        String description,

        @EnumValid(acceptedValues = {"ENABLE", "DISABLE"}, message = "유효하지 않은 상태 값입니다.")
        StatusType status,

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

