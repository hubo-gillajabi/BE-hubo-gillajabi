package com.hubo.gillajabi.crawl.infrastructure.dto.request;

import com.hubo.gillajabi.crawl.domain.constant.MediumTermSkyCondition;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiWeatherMediumTermResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherTermDTO {

    private int lowTemperature3;
    private int highTemperature3;
    private MediumTermSkyCondition skyCondition3;
    private int lowTemperature4;
    private int highTemperature4;
    private MediumTermSkyCondition skyCondition4;
    private int lowTemperature5;
    private int highTemperature5;
    private MediumTermSkyCondition skyCondition5;
    private int lowTemperature6;
    private int highTemperature6;
    private MediumTermSkyCondition skyCondition6;
    private int lowTemperature7;
    private int highTemperature7;
    private MediumTermSkyCondition skyCondition7;

    public static WeatherTermDTO of(ApiWeatherMediumTermResponse.Temperature temperature, ApiWeatherMediumTermResponse.Detail detail){
        WeatherTermDTO dto = new WeatherTermDTO();
        dto.setLowTemperature3(temperature.getTaMin3());
        dto.setHighTemperature3(temperature.getTaMax3());
        dto.setSkyCondition3(MediumTermSkyCondition.fromString(detail.getWf3Am()));
        dto.setLowTemperature4(temperature.getTaMin4());
        dto.setHighTemperature4(temperature.getTaMax4());
        dto.setSkyCondition4(MediumTermSkyCondition.fromString(detail.getWf4Am()));
        dto.setLowTemperature5(temperature.getTaMin5());
        dto.setHighTemperature5(temperature.getTaMax5());
        dto.setSkyCondition5(MediumTermSkyCondition.fromString(detail.getWf5Am()));
        dto.setLowTemperature6(temperature.getTaMin6());
        dto.setHighTemperature6(temperature.getTaMax6());
        dto.setSkyCondition6(MediumTermSkyCondition.fromString(detail.getWf6Am()));
        dto.setLowTemperature7(temperature.getTaMin7());
        dto.setHighTemperature7(temperature.getTaMax7());
        dto.setSkyCondition7(MediumTermSkyCondition.fromString(detail.getWf7Am()));
        return dto;
    }
}
