package com.hubo.gillajabi.course.infrastructure.exception;

import lombok.Getter;

@Getter
public class CourseException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    public CourseException(CourseExceptionCode courseExceptionCode) {
        super(courseExceptionCode.getErrorMessage());
        this.errorCode = courseExceptionCode.getErrorCode();
        this.errorMessage = courseExceptionCode.getErrorMessage();
        this.httpStatus = courseExceptionCode.getHttpStatus();
    }
}
