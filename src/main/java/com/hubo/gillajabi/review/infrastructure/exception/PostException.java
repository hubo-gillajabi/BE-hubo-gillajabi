package com.hubo.gillajabi.review.infrastructure.exception;

import lombok.Getter;

@Getter
public class PostException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    public PostException(PostExceptionCode postExceptionCode) {
        super(postExceptionCode.getErrorMessage());
        this.errorCode = postExceptionCode.getErrorCode();
        this.errorMessage = postExceptionCode.getErrorMessage();
        this.httpStatus = postExceptionCode.getHttpStatus();
    }
}
