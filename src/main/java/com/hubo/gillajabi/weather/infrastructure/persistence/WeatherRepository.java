package com.hubo.gillajabi.weather.infrastructure.persistence;

import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.WeatherCurrentDto;
import com.hubo.gillajabi.weather.application.dto.WeekendWeatherInfoDTO;

import java.util.List;

public interface WeatherRepository {

    WeatherCurrentDto findLatestWeatherByCity(City city);

    WeatherCurrentDto findTodayWeatherByCity(City city);

    List<WeekendWeatherInfoDTO> findWeatherForecastByCity(City city);
}
