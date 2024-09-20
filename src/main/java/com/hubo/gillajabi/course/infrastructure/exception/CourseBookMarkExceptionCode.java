package com.hubo.gillajabi.course.infrastructure.exception;

import lombok.Getter;

@Getter
public enum CourseBookMarkExceptionCode {

    COURSE_BOOK_MARK_NOT_FOUND(1, "북마크를 찾을 수 없습니다.", 404),
    COURSE_BOOK_MARK_ALREADY_EXISTS(2, "이미 북마크 중 입니다.", 400);

    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    CourseBookMarkExceptionCode(int errorCode, String errorMessage, int httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

}
