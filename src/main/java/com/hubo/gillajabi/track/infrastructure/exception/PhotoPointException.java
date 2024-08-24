package com.hubo.gillajabi.track.infrastructure.exception;

import lombok.Getter;


@Getter
public class PhotoPointException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    public PhotoPointException(PhotoPointExceptionCode photoPointExceptionCode) {
        super(photoPointExceptionCode.getErrorMessage());
        this.errorCode = photoPointExceptionCode.getErrorCode();
        this.errorMessage = photoPointExceptionCode.getErrorMessage();
        this.httpStatus = photoPointExceptionCode.getHttpStatus();
    }
}