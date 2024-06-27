package com.hubo.gillajabi.crawl.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

public class ApiWeatherAlertResponse extends AbstractApiResponse<ApiWeatherAlertResponse.Alert> {


    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Alert {
       //TODO : 예보 시스템
    }
}
