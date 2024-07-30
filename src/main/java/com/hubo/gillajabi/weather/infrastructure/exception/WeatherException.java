package com.hubo.gillajabi.weather.infrastructure.exception;

import lombok.Getter;

@Getter
public class WeatherException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    public WeatherException(int errorCode, String errorMessage, int httpStatus) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public WeatherException(WeatherExceptionCode weatherExceptionCode) {
        this(weatherExceptionCode.getErrorCode(), weatherExceptionCode.getErrorMessage(), weatherExceptionCode.getHttpStatus());
    }

}
