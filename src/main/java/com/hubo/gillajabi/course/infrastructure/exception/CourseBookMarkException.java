package com.hubo.gillajabi.course.infrastructure.exception;

import lombok.Getter;

@Getter
public class CourseBookMarkException extends RuntimeException {

    private final int errorCode;

    private final String errorMessage;

    private final int httpStatus;

    public CourseBookMarkException(CourseBookMarkExceptionCode courseBookMarkExceptionCode) {
        super(courseBookMarkExceptionCode.getErrorMessage());
        this.errorCode = courseBookMarkExceptionCode.getErrorCode();
        this.errorMessage = courseBookMarkExceptionCode.getErrorMessage();
        this.httpStatus = courseBookMarkExceptionCode.getHttpStatus();
    }
}
