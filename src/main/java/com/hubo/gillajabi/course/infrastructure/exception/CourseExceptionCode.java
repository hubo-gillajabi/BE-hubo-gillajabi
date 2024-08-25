package com.hubo.gillajabi.course.infrastructure.exception;

import lombok.Getter;

@Getter
public enum CourseExceptionCode {
    COURSE_NOT_FOUND(1, "코스를 찾을 수 없습니다.", 404),
    COURSE_GPS_NOT_FOUND(2, "코스 GPS를 찾을 수 없습니다.", 404),
    COURSE_ELEVATION_NOT_FOUND(3, "코스 고도를 찾을 수 없습니다.", 404);

    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    CourseExceptionCode(int errorCode, String errorMessage, int httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
