package com.hubo.gillajabi.weather.domain.service;

import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.city.infrastructure.persistence.CityRepository;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.WeatherCurrentDto;
import com.hubo.gillajabi.weather.application.dto.WeekendWeatherInfoDTO;
import com.hubo.gillajabi.weather.application.dto.response.CurrentWeatherResponse;
import com.hubo.gillajabi.weather.application.dto.response.WeatherForecastResponse;
import com.hubo.gillajabi.weather.infrastructure.persistence.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeatherService {

    private final WeatherRepository weatherRepository;

    private final CityRepository cityRepository;

    public CurrentWeatherResponse getCurrentWeather(Long cityId) {
        City city = cityRepository.getEntityById(cityId);

        WeatherCurrentDto weatherData = weatherRepository.findLatestWeatherByCity(city);

        return CurrentWeatherResponse.from(weatherData);
    }

    public WeatherForecastResponse getWeatherForecast(Long cityId) {
        City city = cityRepository.getEntityById(cityId);

        WeatherCurrentDto weatherTodayData = weatherRepository.findTodayWeatherByCity(city);

        List<WeekendWeatherInfoDTO> weatherForecastData = weatherRepository.findWeatherForecastByCity(city);

        return WeatherForecastResponse.of(weatherTodayData, weatherForecastData);
    }
}
