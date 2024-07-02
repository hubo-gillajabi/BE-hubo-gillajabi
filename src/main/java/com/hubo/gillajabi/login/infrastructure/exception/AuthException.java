package com.hubo.gillajabi.login.infrastructure.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private final int errorCode;
    private final String errorMessage;

    public AuthException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public AuthException(AuthExceptionCode authExceptionCode) {
        this(authExceptionCode.getErrorCode(), authExceptionCode.getErrorMessage());
    }
}
