package com.hubo.gillajabi.admin.infrastructure.exception;

import lombok.Getter;

@Getter
public enum AdminLoginExceptionCode {
    ADMIN_NOT_FOUND(91, "관리자를 찾을 수 없습니다.", 404),
    INVALID_PASSWORD(92, "비밀번호가 일치하지 않습니다.", 400);

    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    AdminLoginExceptionCode(int errorCode, String errorMessage, int httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
