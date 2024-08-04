package com.hubo.gillajabi.agreement.infrastructure.exception;

import lombok.Getter;

@Getter
public class AgreementException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;

    public AgreementException(AgreementExceptionCode agreementExceptionCode) {
        super(agreementExceptionCode.getErrorMessage());
        this.errorCode = agreementExceptionCode.getErrorCode();
        this.errorMessage = agreementExceptionCode.getErrorMessage();
    }
}
