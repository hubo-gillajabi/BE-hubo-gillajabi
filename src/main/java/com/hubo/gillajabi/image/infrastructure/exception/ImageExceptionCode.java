package com.hubo.gillajabi.image.infrastructure.exception;


import lombok.Getter;

@Getter
public enum ImageExceptionCode {
    INVALID_IMAGE_FORMAT(0, "유효하지 않은 이미지 형식입니다.", 400),
    IMAGE_SIZE_TOO_LARGE(1, "이미지 크기가 너무 큽니다.", 413),
    IMAGE_UPLOAD_FAILED(2, "이미지 업로드에 실패하였습니다.", 500),
    IMAGE_DELETE_FAILED(3, "이미지 삭제에 실패하였습니다.", 500),
    IMAGE_NOT_VALID(4, "유효하지 않은 이미지입니다.", 400);

    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    ImageExceptionCode(int errorCode, String errorMessage, int httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}