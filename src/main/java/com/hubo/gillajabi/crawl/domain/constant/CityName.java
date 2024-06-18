package com.hubo.gillajabi.crawl.domain.constant;

public enum CityName {
    DURU("두루누비"),
    BUSAN("부산");

    private final String name;

    CityName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
