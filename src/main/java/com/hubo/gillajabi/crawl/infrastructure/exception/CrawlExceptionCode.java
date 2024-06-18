package com.hubo.gillajabi.crawl.infrastructure.exception;

public enum CrawlExceptionCode {
    INVALID_REQUEST_PARAMETER_ERROR(10, "잘못된 요청 파라메터 에러"),
    NO_MANDATORY_REQUEST_PARAMETERS_ERROR(11, "필수 요청 파라메터가 없음"),
    TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR(21, "일시적으로 사용할 수 없는 서비스키"),
    UNSIGNED_CALL_ERROR(33, "서명되지 않은 호출"),
    SERVICETIMEOUT_ERROR(5, "서비스 연결 실패 에러"),
    NODATA_ERROR(3, "데이터 없음 에러"),
    DB_ERROR(2, "데이터베이스 에러");

    private final int code;
    private final String message;

    CrawlExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
