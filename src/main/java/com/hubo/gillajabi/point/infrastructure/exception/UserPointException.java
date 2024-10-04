package com.hubo.gillajabi.point.infrastructure.exception;

import lombok.Getter;

@Getter
public class UserPointException extends RuntimeException {

    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    public UserPointException(UserPointExceptionCode userPointExceptionCode) {
        super(userPointExceptionCode.getErrorMessage());
        this.errorCode = userPointExceptionCode.getErrorCode();
        this.errorMessage = userPointExceptionCode.getErrorMessage();
        this.httpStatus = userPointExceptionCode.getHttpStatus();
    }
}
