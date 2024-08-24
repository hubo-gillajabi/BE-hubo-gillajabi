package com.hubo.gillajabi.track.infrastructure.exception;

import lombok.Getter;

@Getter
public enum TrackExceptionCode {
    NOT_TRACKING(1, "현재 트래킹중이 아닙니다.", 400),
    ALREADY_TRACKING(2, "현재 트래킹중입니다.", 400),
    TRACK_NOT_FOUND(3, "트래킹을 찾을 수 없습니다.", 404),
    NOT_FOUND_TRACKING(4, "트래킹 기록을 찾을수 없습니다", 404);

    private final int errorCode;
    private final String errorMessage;
    private final int httpStatus;

    TrackExceptionCode(int errorCode, String errorMessage, int httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }


}
