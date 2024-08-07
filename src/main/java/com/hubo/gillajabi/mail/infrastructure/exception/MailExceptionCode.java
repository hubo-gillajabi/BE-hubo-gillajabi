package com.hubo.gillajabi.mail.infrastructure.exception;

import lombok.Getter;

@Getter
public enum MailExceptionCode {
    MAIL_FAILED_TO_SEND(0, "메일 전송에 실패했습니다.");

    private final int errorCode;
    private final String errorMessage;

    MailExceptionCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
