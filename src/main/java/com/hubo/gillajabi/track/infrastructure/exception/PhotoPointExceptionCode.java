package com.hubo.gillajabi.track.infrastructure.exception;

import lombok.Getter;

@Getter
public enum PhotoPointExceptionCode {
    PHOTO_POINT_EXCPEITON_CODE(1, "사진 포인트를 찾을 수 없습니다.", 404),
    PHOTO_POINT_CREATED_ERROR(2, "사진 포인트를 생성할 수 없습니다.", 500);

    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    PhotoPointExceptionCode(int errorCode, String errorMessage, int httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
