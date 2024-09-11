package com.hubo.gillajabi.review.application.dto.request;

import com.hubo.gillajabi.global.ImageUrlsProvider;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PostWriteRequest(

        @NotNull(message = "트랙 ID는 필수 입력 값입니다.")
        Long trackRecordId,

        @Size(min = 1, max = 100, message = "제목은 1자 이상 100자 이하여야 합니다.")
        String title,

        @Size(min = 0, max = 500, message = "내용은 500자 이하여야 합니다.")
        String content,

        @NotNull(message = "별점은 필수 입력 값입니다.")
        @Min(value = 1, message = "별점은 최소 1이어야 합니다.")
        @Max(value = 5, message = "별점은 최대 5이어야 합니다.")
        Integer star,

        List<String> tags,

        List<String> imageUrls

) implements ImageUrlsProvider {

    @Override
    public List<String> getImageUrls() {
       return imageUrls;
    }
}
