package com.hubo.gillajabi.mail.infrastructure.exception;

import lombok.Getter;

@Getter
public class MailException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;

    public MailException(MailExceptionCode mailExceptionCode) {
        super(mailExceptionCode.getErrorMessage());
        this.errorCode = mailExceptionCode.getErrorCode();
        this.errorMessage = mailExceptionCode.getErrorMessage();
    }
}

