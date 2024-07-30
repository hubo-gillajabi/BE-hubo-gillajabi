package com.hubo.gillajabi.weather.application.dto.response;

import com.hubo.gillajabi.crawl.infrastructure.dto.request.WeatherCurrentDto;
import com.hubo.gillajabi.weather.application.dto.TodayWeatherDetailInfoDTO;
import com.hubo.gillajabi.weather.application.dto.WeekendWeatherInfoDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class WeatherForecastResponse {
    private TodayWeatherDetailInfoDTO todayWeatherDetailInfo;
    private List<WeekendWeatherInfoDTO> weekendWeatherInfo;

    public static WeatherForecastResponse of(WeatherCurrentDto weatherTodayData, List<WeekendWeatherInfoDTO> weatherForecastData) {
        WeatherForecastResponse weatherForecastResponse = new WeatherForecastResponse();
        weatherForecastResponse.todayWeatherDetailInfo = TodayWeatherDetailInfoDTO.from(weatherTodayData);
        weatherForecastResponse.weekendWeatherInfo = weatherForecastData;

        return weatherForecastResponse;
    }
}
