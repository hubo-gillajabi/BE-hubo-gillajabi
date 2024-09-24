package com.hubo.gillajabi.point.infrastructure.exception;

import lombok.Getter;

@Getter
public enum UserPointExceptionCode {
    NOT_FOUND_USER_POINT(1, "포인트를 찾을 수 없습니다.", 404),
    ALREADY_USER_POINT(2, "이미 포인트가 존재합니다.", 400),
    NOT_OWNER(3, "포인트 소유자가 아닙니다.", 403);

    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    UserPointExceptionCode(int errorCode, String errorMessage, int httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
