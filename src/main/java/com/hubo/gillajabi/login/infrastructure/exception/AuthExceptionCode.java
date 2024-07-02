package com.hubo.gillajabi.login.infrastructure.exception;

import lombok.Getter;

@Getter
public enum AuthExceptionCode {
    // 올바르지 않은 권한
    INVALID_ROLE( 0,"접근이 거부되었습니다. 유효한 권한이 없습니다."),
    TOKEN_EXPIRED(1, "토큰이 만료되었습니다."),
    INVALID_TOKEN(2, "토큰이 유효하지 않습니다."),
    ACCESS_DENIED(3, "접근이 거부되었습니다."),
    CREDENTIALS_EXPIRED(4, "인증 정보가 만료되었습니다.");

    private final int errorCode;
    private final String errorMessage;

    AuthExceptionCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
