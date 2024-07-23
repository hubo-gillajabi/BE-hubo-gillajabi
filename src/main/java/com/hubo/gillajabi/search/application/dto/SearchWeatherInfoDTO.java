package com.hubo.gillajabi.search.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchWeatherInfoDTO {

    private Float lowestTemperature;

    private Float highestTemperature;

    private String condition;
}
