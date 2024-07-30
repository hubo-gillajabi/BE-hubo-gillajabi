package com.hubo.gillajabi.weather.infrastructure.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.constant.WeatherRedisConstants;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.WeatherCurrentDto;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.WeatherTermDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiWeatherMediumTermResponse;
import com.hubo.gillajabi.weather.application.dto.WeekendWeatherInfoDTO;
import com.hubo.gillajabi.weather.infrastructure.exception.WeatherException;
import com.hubo.gillajabi.weather.infrastructure.exception.WeatherExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class WeatherRepositoryImpl implements WeatherRepository {

    private final ObjectMapper objectMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public WeatherCurrentDto findLatestWeatherByCity(City city) {
        String weatherKey = WeatherRedisConstants.makeWeatherKey(city);
        Object weatherObject = redisTemplate.opsForValue().get(weatherKey);

        if (weatherObject != null) {
            try {
                return objectMapper.convertValue(weatherObject, WeatherCurrentDto.class);
            } catch (IllegalArgumentException e) {
                throw new WeatherException(WeatherExceptionCode.WEATHER_PARSING_ERROR);
            }
        }

        throw new WeatherException(WeatherExceptionCode.WEATHER_NOT_FOUND);
    }

    @Override
    public WeatherCurrentDto findTodayWeatherByCity(City city) {
        final WeatherCurrentDto liveWeather = findLatestWeatherByCity(city);

        final String todayWeatherKey = WeatherRedisConstants.makeWeatherKey(city, LocalDate.now());
        Object todayWeatherObject = redisTemplate.opsForValue().get(todayWeatherKey);

        WeatherCurrentDto weatherCurrentDto = null;
        try{
            if(todayWeatherObject != null){
                weatherCurrentDto = objectMapper.convertValue(todayWeatherObject, WeatherCurrentDto.class);
            }
        } catch (IllegalArgumentException e) {
            throw new WeatherException(WeatherExceptionCode.WEATHER_PARSING_ERROR);
        }

        liveWeather.setLowTemperature(Objects.requireNonNull(weatherCurrentDto).getLowTemperature());
        liveWeather.setHighTemperature(weatherCurrentDto.getHighTemperature());

        return liveWeather;
    }

    @Override
    public List<WeekendWeatherInfoDTO> findWeatherForecastByCity(City city) {
        List<WeekendWeatherInfoDTO> weekForecast = new ArrayList<>();
        LocalDate today = LocalDate.now();

        final WeatherCurrentDto plusOneDayWeather = findWeatherByCityAndDate(city, today.plusDays(1));
        weekForecast.add( WeekendWeatherInfoDTO.from(plusOneDayWeather));

        final WeatherCurrentDto plusTwoDayWeather = findWeatherByCityAndDate(city, today.plusDays(2));
        weekForecast.add( WeekendWeatherInfoDTO.from(plusTwoDayWeather));

        final WeatherTermDTO mediumTermWeather = findMediumTermWeatherByCityAndDate(city, today.plusDays(3));
        weekForecast.addAll(WeekendWeatherInfoDTO.from(Objects.requireNonNull(mediumTermWeather)));

        return weekForecast;
    }

    private WeatherCurrentDto findWeatherByCityAndDate(City city, LocalDate localDate) {
        final String weatherKey = WeatherRedisConstants.makeWeatherKey(city, localDate);
        Object weatherObject = redisTemplate.opsForValue().get(weatherKey);

        if (weatherObject != null) {
            try {
                return objectMapper.convertValue(weatherObject, WeatherCurrentDto.class);
            } catch (IllegalArgumentException e) {
                throw new WeatherException(WeatherExceptionCode.WEATHER_PARSING_ERROR);
            }
        }

        throw new WeatherException(WeatherExceptionCode.WEATHER_NOT_FOUND);
    }

    private WeatherTermDTO findMediumTermWeatherByCityAndDate(City city, LocalDate localDate) {
        final String weatherKey = WeatherRedisConstants.makeWeatherKey(city, localDate);
        Object weatherObject = redisTemplate.opsForValue().get(weatherKey);

        if (weatherObject != null) {
            try {
                return objectMapper.convertValue(weatherObject, WeatherTermDTO.class);
            } catch (IllegalArgumentException e) {
                throw new WeatherException(WeatherExceptionCode.WEATHER_PARSING_ERROR);
            }
        }
        return null;
    }
}
