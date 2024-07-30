package com.hubo.gillajabi.weather.infrastructure.exception;

import lombok.Getter;

@Getter
public enum WeatherExceptionCode {
    WEATHER_NOT_FOUND(1, "날씨 정보를 찾을 수 없습니다.",404),
    WEATHER_PARSING_ERROR(2, "날씨 정보를 파싱할 수 없습니다.",500);

    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;


    WeatherExceptionCode(int errorCode, String errorMessage, int httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
