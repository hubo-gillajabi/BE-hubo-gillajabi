package com.hubo.gillajabi.crawl.domain.constant;

import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;

public enum CycleType {
    CYCLE, SINGLE;

    public static CycleType fromValue(ApiCourseResponse.Course course) {
        return switch (course.getCrsCycle()) {
            case "순환형" -> CYCLE;
            case "비순환형" -> SINGLE;
            default -> throw new IllegalArgumentException("순환형, 비순환형 판별 안됨 " + course.getCrsCycle());
        };
    }
}
