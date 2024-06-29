package com.hubo.gillajabi.crawl.infrastructure.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiCourseResponse extends AbstractApiResponse<ApiCourseResponse.Course> {

    @Getter
    @Setter
    public static class Course {
        private String routeIdx;
        private String crsIdx;
        private String crsKorNm;
        private String crsDstnc;
        private String crsTotlRqrmHour;
        private String crsLevel;
        private String crsCycle;
        private String crsContents;
        private String crsSummary;
        private String crsTourInfo;
        private String travelerinfo;
        private String sigun;
        private String brdDiv;
        private String gpxpath;
        private String createdtime;
        private String modifiedtime;
    }
}
