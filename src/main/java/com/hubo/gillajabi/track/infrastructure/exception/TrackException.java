package com.hubo.gillajabi.track.infrastructure.exception;

import lombok.Getter;

@Getter
public class TrackException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    public TrackException(TrackExceptionCode trackExceptionCode) {
        super(trackExceptionCode.getErrorMessage());
        this.errorCode = trackExceptionCode.getErrorCode();
        this.errorMessage = trackExceptionCode.getErrorMessage();
        this.httpStatus = trackExceptionCode.getHttpStatus();
    }
}
