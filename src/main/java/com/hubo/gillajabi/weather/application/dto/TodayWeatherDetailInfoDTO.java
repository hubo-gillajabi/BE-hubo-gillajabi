package com.hubo.gillajabi.weather.application.dto;

import com.hubo.gillajabi.crawl.domain.constant.PrecipitationForm;
import com.hubo.gillajabi.crawl.domain.constant.SkyCondition;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.WeatherCurrentDto;
import lombok.Getter;

@Getter
public class TodayWeatherDetailInfoDTO {

    // 현재 기온
    private Float liveTemperature;

    // 최대 최저 기온
    private Float lowTemperature;

    private Float highTemperature;

    // 현재 날씨 상태
    private SkyCondition skyCondition;

    // 강수 형태
    private PrecipitationForm precipitationForm;

    // 강수확률
    private Integer precipitationProbability;

    //강수량
    private Float precipitationAmount;

    // 적설량
    private Float snowAmount;

    // 풍속 (m/s)
    private Float windSpeed;

    // 습도 (%)
    private Integer humidity;


    public static TodayWeatherDetailInfoDTO from(WeatherCurrentDto weatherTodayData) {
        TodayWeatherDetailInfoDTO todayWeatherDetailInfoDTO = new TodayWeatherDetailInfoDTO();
        todayWeatherDetailInfoDTO.liveTemperature = weatherTodayData.getLiveTemperature();
        todayWeatherDetailInfoDTO.lowTemperature = weatherTodayData.getLowTemperature();
        todayWeatherDetailInfoDTO.highTemperature = weatherTodayData.getHighTemperature();
        todayWeatherDetailInfoDTO.skyCondition = weatherTodayData.getSkyCondition();
        todayWeatherDetailInfoDTO.precipitationForm = weatherTodayData.getPrecipitationForm();
        todayWeatherDetailInfoDTO.precipitationProbability = weatherTodayData.getPrecipitationProbability();
        todayWeatherDetailInfoDTO.precipitationAmount = weatherTodayData.getPrecipitationAmount();
        todayWeatherDetailInfoDTO.snowAmount = weatherTodayData.getSnowfallAmount();
        todayWeatherDetailInfoDTO.windSpeed = weatherTodayData.getWindSpeed();
        todayWeatherDetailInfoDTO.humidity = weatherTodayData.getHumidity();

        return todayWeatherDetailInfoDTO;
    }
}
