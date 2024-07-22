package com.hubo.gillajabi.crawl.domain.constant;

public enum Province {
    SEOUL("서울"),
    BUSAN("부산"),
    DAEGU("대구"),
    INCHEON("인천"),
    GWANGJU("광주"),
    DAEJEON("대전"),
    ULSAN("울산"),
    SEJONG("세종"),
    GYEONGGI("경기"),
    GANGWON("강원"),
    CHUNGBUK("충북"),
    CHUNGNAM("충남"),
    JEONBUK("전북"),
    JEONNAM("전남"),
    GYEONGBUK("경북"),
    GYEONGNAM("경남"),
    JEJU("제주");


    private final String value;

    Province(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Province fromValue(String value) {
        for (Province province : Province.values()) {
            if (province.getValue().equals(value)) {
                return province;
            }
        }
        throw new IllegalArgumentException("해당 지역명이 enum에 없습니다. " + value);
    }

    public boolean isBigCity() {
        return switch (this) {
            case SEOUL, BUSAN, DAEGU, INCHEON, GWANGJU, DAEJEON, ULSAN, SEJONG -> true;
            default -> false;
        };
    }}