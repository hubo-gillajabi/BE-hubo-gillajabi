package com.hubo.gillajabi.crawl.domain.constant;

public enum CityCrawlName {
    DURU("두루누비"),
    BUSAN("부산");

    private final String name;

    CityCrawlName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
