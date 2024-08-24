package com.hubo.gillajabi.place.domain.constant;

public enum PlaceType {
    CONVENIENCE_STORE("편의점"),
    EVENT("행사"),
    PUBLIC_TRANSPORT("대중교통"),
    RESTAURANT("식당"),
    CAFE("카페");

    private final String koreanName;

    PlaceType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static PlaceType fromKoreanName(String koreanName) {
        for (PlaceType type : values()) {
            if (type.getKoreanName().equals(koreanName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("PlaceType 변환 문제" + koreanName);
    }
}