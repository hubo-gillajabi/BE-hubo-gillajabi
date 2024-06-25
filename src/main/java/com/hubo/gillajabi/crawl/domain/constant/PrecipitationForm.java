package com.hubo.gillajabi.crawl.domain.constant;

public enum PrecipitationForm {
    NONE(0, "없음"),
    RAIN(1, "비"),
    RAIN_AND_SNOW(2, "비/눈"),
    SNOW(3, "눈"),
    SHOWER(4, "소나기");

    private final int code;
    private final String description;

    PrecipitationForm(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PrecipitationForm fromCode(int code) {
        return switch (code) {
            case 0 -> NONE;
            case 1 -> RAIN;
            case 2 -> RAIN_AND_SNOW;
            case 3 -> SNOW;
            case 4 -> SHOWER;
            default -> throw new IllegalArgumentException("잘못된 강수형태 " + code);
        };
    }
}
