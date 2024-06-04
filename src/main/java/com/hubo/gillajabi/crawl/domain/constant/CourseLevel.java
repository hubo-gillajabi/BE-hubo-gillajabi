package com.hubo.gillajabi.crawl.domain.constant;

import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;

public enum CourseLevel {
    HIGH, MIDDLE, LOW;

    public static CourseLevel fromValue(DuruCourseResponse.Course course) {
        return switch (course.getCrsLevel()) {
            case "1" -> HIGH;
            case "2" -> MIDDLE;
            case "3" -> LOW;
            default -> throw new IllegalArgumentException("잘못된 레벨 값 : " + course.getCrsLevel());
        };
    }
}
