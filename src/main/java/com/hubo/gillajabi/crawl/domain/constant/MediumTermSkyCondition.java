package com.hubo.gillajabi.crawl.domain.constant;

public enum MediumTermSkyCondition {
    // 맑음
    CLEAR("맑음"),

    // 구름많음, 구름많고 비, 구름많고 눈, 구름많고 비/눈, 구름많고 소나기
    MOSTLY_CLOUDY("구름많음"),
    MOSTLY_CLOUDY_WITH_RAIN("구름많고 비"),
    MOSTLY_CLOUDY_WITH_SNOW("구름많고 눈"),
    MOSTLY_CLOUDY_WITH_RAIN_AND_SNOW("구름많고 비/눈"),
    MOSTLY_CLOUDY_WITH_SHOWERS("구름많고 소나기"),

    // 흐림, 흐리고 비, 흐리고 눈, 흐리고 비/눈, 흐리고 소나기
    CLOUDY("흐림"),
    CLOUDY_WITH_RAIN("흐리고 비"),
    CLOUDY_WITH_SNOW("흐리고 눈"),
    CLOUDY_WITH_RAIN_AND_SNOW("흐리고 비/눈"),
    CLOUDY_WITH_SHOWERS("흐리고 소나기");

    private final String description;

    MediumTermSkyCondition(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static MediumTermSkyCondition fromString(String value){
        return switch (value) {
            case "맑음" -> CLEAR;
            case "구름많음" -> MOSTLY_CLOUDY;
            case "구름많고 비" -> MOSTLY_CLOUDY_WITH_RAIN;
            case "구름많고 눈" -> MOSTLY_CLOUDY_WITH_SNOW;
            case "구름많고 비/눈" -> MOSTLY_CLOUDY_WITH_RAIN_AND_SNOW;
            case "구름많고 소나기" -> MOSTLY_CLOUDY_WITH_SHOWERS;
            case "흐림" -> CLOUDY;
            case "흐리고 비" -> CLOUDY_WITH_RAIN;
            case "흐리고 눈" -> CLOUDY_WITH_SNOW;
            case "흐리고 비/눈" -> CLOUDY_WITH_RAIN_AND_SNOW;
            case "흐리고 소나기" -> CLOUDY_WITH_SHOWERS;
            default -> throw new IllegalArgumentException("잘못된 하늘 상태 : " + value);
        };
    }
}
