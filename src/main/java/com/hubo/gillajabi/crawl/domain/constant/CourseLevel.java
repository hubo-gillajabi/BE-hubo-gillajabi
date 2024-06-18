package com.hubo.gillajabi.crawl.domain.constant;

public enum CourseLevel {
    HIGH, MIDDLE, LOW;

    public static CourseLevel fromValue(String level) {
        return switch (level) {
            case "1" -> LOW;
            case "2" -> MIDDLE;
            case "3" -> HIGH;
            default -> throw new IllegalArgumentException("잘못된 레벨 값 : " + level);
        };
    }
}
