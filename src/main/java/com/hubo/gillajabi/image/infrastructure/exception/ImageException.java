package com.hubo.gillajabi.image.infrastructure.exception;
import lombok.Getter;

@Getter
public class ImageException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    public ImageException(int errorCode, String errorMessage, int httpStatus) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public ImageException(ImageExceptionCode imageExceptionCode) {
        this(imageExceptionCode.getErrorCode(), imageExceptionCode.getErrorMessage(), imageExceptionCode.getHttpStatus());
    }
}
