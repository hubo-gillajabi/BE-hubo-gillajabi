package com.hubo.gillajabi.login.infrastructure.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    public AuthException(int errorCode, String errorMessage, int httpStatus) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public AuthException(AuthExceptionCode authExceptionCode) {
        this(authExceptionCode.getErrorCode(), authExceptionCode.getErrorMessage(), authExceptionCode.getHttpStatus());
    }
}
