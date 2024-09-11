package com.hubo.gillajabi.review.infrastructure.exception;

import lombok.Getter;

@Getter
public enum PostExceptionCode {
    NOT_FOUND_POST(1, "게시글을 찾을 수 없습니다.", 404),
    ALREADY_POST(2, "이미 게시글이 존재합니다.", 400),
    NOT_OWNER(3, "게시글 작성자가 아닙니다.", 403);

    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    PostExceptionCode(int errorCode, String errorMessage, int httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
