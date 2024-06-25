package com.hubo.gillajabi.crawl.domain.constant;

import lombok.Getter;

@Getter
public enum SkyCondition {
    CLEAR(1, "맑음"),
    PARTLY_CLOUDY(2, "구름조금"),
    MOSTLY_CLOUDY(3, "구름많음"),
    CLOUDY(4, "흐림");

    private final int code;
    private final String description;

    SkyCondition(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static SkyCondition fromCode(int code) {
        return switch (code) {
            case 1 -> CLEAR;
            case 2 -> PARTLY_CLOUDY;
            case 3 -> MOSTLY_CLOUDY;
            case 4 -> CLOUDY;
            default -> throw new IllegalArgumentException("잘못된 하늘 상태 코드 : " + code);
        };
    }
}
