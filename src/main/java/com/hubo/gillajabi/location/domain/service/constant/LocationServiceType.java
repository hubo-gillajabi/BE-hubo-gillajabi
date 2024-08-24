package com.hubo.gillajabi.location.domain.service.constant;

/**
 * 위치정보 수집 서비스 ( ex : 트래킹 )
 */
public enum LocationServiceType {

    TRACKING("트래킹"),
    COURSE_LOCATION_INQUIRY("코스 위치 조회");

    private final String description;

    LocationServiceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
