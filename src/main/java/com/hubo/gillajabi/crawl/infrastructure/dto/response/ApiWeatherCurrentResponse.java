package com.hubo.gillajabi.crawl.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


public class ApiWeatherCurrentResponse extends AbstractApiResponse<ApiWeatherCurrentResponse.Current> {

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Current {
        private String baseDate;
        private String baseTime;
        private String category;
        private String fcstDate;
        private String fcstTime;
        private String fcstValue;
        private int nx;
        private int ny;
    }
}

