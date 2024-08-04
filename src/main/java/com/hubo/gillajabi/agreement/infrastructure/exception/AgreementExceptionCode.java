package com.hubo.gillajabi.agreement.infrastructure.exception;

import lombok.Getter;

@Getter
public enum AgreementExceptionCode {
    AGREEMENT_NOT_FOUND(0, "해당 약관을 찾을 수 없습니다.", 404),
    AGREEMENT_ALREADY_EXISTS(1, "이미 존재하는 약관입니다.", 409),
    AGREEMENT_INVALID(2, "약관이 유효하지 않습니다.", 400);

    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    AgreementExceptionCode(int errorCode, String errorMessage, int httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}