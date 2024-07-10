package com.hubo.gillajabi.admin.infrastructure.exception;

import lombok.Getter;

@Getter
public class AdminLoginException extends RuntimeException {

    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    public AdminLoginException(int errorCode, String errorMessage, int httpStatus) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public AdminLoginException(AdminLoginExceptionCode adminLoginExceptionCode){
        this(adminLoginExceptionCode.getErrorCode(), adminLoginExceptionCode.getErrorMessage(), adminLoginExceptionCode.getHttpStatus());
    }

}
