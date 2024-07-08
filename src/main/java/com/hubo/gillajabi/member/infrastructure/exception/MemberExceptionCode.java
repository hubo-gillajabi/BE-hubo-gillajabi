package com.hubo.gillajabi.member.infrastructure.exception;

import lombok.Getter;

@Getter
public enum MemberExceptionCode {
    MEMBER_NOT_FOUND(0, "회원 정보를 찾을 수 없습니다.", 404),
    INVALID_MEMBER_STATUS(1, "회원 상태가 유효하지 않습니다.", 400);

    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    MemberExceptionCode(int errorCode, String errorMessage, int httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}

