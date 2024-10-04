package com.hubo.gillajabi.point.application.dto.request;

import com.hubo.gillajabi.global.ImageUrlsProvider;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record UserPointRequest(

        @NotNull(message = "내용은 필수 값입니다.")
        @Size(min = 1, max = 80, message = "내용은 1자 이상 80자 이하 여야 합니다.")
        String content,

        @NotNull(message = "위도는 필수 값입니다.")
        @DecimalMin(value = "-90.0", message = "위도는 -90도 이상이어야 합니다.")
        @DecimalMax(value = "90.0", message = "위도는 90도 이하이어야 합니다.")
        BigDecimal latitude,

        @NotNull(message = "경도는 필수 값입니다.")
        @DecimalMin(value = "-180.0", message = "경도는 -180도 이상이어야 합니다.")
        @DecimalMax(value = "180.0", message = "경도는 180도 이하이어야 합니다.")
        BigDecimal longitude,

        @NotNull(message = "이미지 URL은 필수 값입니다.")
        @Size(min = 1, max = 5, message = "이미지 URL은 최소 1개 이상, 최대 5개 이하 여야 합니다.")
        List<String> imageUrls

) implements ImageUrlsProvider {
    @Override
    public List<String> getImageUrls() {
        return imageUrls;
    }
}
