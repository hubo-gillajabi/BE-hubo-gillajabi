package com.hubo.gillajabi.member.infrastructure.exception;
import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    public MemberException(int errorCode, String errorMessage, int httpStatus) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public MemberException(MemberExceptionCode memberExceptionCode) {
        this(memberExceptionCode.getErrorCode(), memberExceptionCode.getErrorMessage(), memberExceptionCode.getHttpStatus());
    }

}
