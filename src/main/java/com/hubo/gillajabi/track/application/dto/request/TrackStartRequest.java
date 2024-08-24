package com.hubo.gillajabi.track.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackStartRequest {

    @NotNull(message = "위도는 필수 입력값입니다.")
    @DecimalMin(value = "-90.0", message = "위도는 -90.0 이상이어야 합니다.")
    @DecimalMax(value = "90.0", message = "위도는 90.0 이하이어야 합니다.")
    @Digits(integer = 2, fraction = 8, message = "위도는 소수점 8자리까지만 허용됩니다.")
    private BigDecimal latitude;

    @NotNull(message = "경도는 필수 입력값입니다.")
    @DecimalMin(value = "-180.0", message = "경도는 -180.0 이상이어야 합니다.")
    @DecimalMax(value = "180.0", message = "경도는 180.0 이하이어야 합니다.")
    @Digits(integer = 3, fraction = 8, message = "경도는 소수점 8자리까지만 허용됩니다.")
    private BigDecimal longitude;

    @Positive(message = "코스 ID는 양수여야 합니다.")
    @NotNull(message = "코스 ID는 필수 입력값입니다.")
    private Long courseId;

}