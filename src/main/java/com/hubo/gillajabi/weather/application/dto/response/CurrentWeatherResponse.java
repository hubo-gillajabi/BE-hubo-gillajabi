package com.hubo.gillajabi.weather.application.dto.response;

import com.hubo.gillajabi.crawl.domain.constant.PrecipitationForm;
import com.hubo.gillajabi.crawl.domain.constant.SkyCondition;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.WeatherCurrentDto;
import lombok.Getter;


import java.util.Optional;

@Getter
public class CurrentWeatherResponse {

    private final Float liveTemperature;
    private final PrecipitationForm precipitationForm;
    private final SkyCondition skyCondition;

    private CurrentWeatherResponse(Float liveTemperature, PrecipitationForm precipitationForm, SkyCondition skyCondition) {
        this.liveTemperature = liveTemperature;
        this.precipitationForm = precipitationForm;
        this.skyCondition = skyCondition;
    }

    public static CurrentWeatherResponse from(WeatherCurrentDto weatherData) {
        return new CurrentWeatherResponse(
                Optional.ofNullable(weatherData.getLiveTemperature()).orElse(0.0f),
                Optional.ofNullable(weatherData.getPrecipitationForm()).orElse(PrecipitationForm.NONE),
                Optional.ofNullable(weatherData.getSkyCondition()).orElse(SkyCondition.CLEAR)
        );
    }
}
