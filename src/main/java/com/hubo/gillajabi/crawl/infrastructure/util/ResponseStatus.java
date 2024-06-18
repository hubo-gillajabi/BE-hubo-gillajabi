package com.hubo.gillajabi.crawl.infrastructure.util;

public enum ResponseStatus {
    INVALID_REQUEST_PARAMETER_ERROR(10, "잘못된 요청 파라메터 에러"),
    NO_MANDATORY_REQUEST_PARAMETERS_ERROR(11, "필수 요청 파라메터가 없음"),
    TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR(21, "일시적으로 사용할 수 없는 서비스키"),
    UNSIGNED_CALL_ERROR(33, "서명되지 않은 호출"),
    OK(0000, "OK"),  // 성공 응답 코드 추가
    UNKNOWN_ERROR(-1, "알 수 없는 에러");

    private final int code;
    private final String description;

    ResponseStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ResponseStatus fromCode(int code) {
        for (ResponseStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return UNKNOWN_ERROR;
    }
}
