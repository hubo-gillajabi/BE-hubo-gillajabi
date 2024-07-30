package com.hubo.gillajabi.weather.application.resolver;

import com.hubo.gillajabi.login.application.annotation.UserOnly;
import com.hubo.gillajabi.weather.application.dto.response.CurrentWeatherResponse;
import com.hubo.gillajabi.weather.application.dto.response.WeatherForecastResponse;
import com.hubo.gillajabi.weather.domain.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WeatherResolver {

    private final WeatherService weatherService;

    @QueryMapping
    @UserOnly
    public CurrentWeatherResponse getCurrentWeather(@Argument Long cityId) {
        return weatherService.getCurrentWeather(cityId);
    }

    @QueryMapping
    @UserOnly
    public WeatherForecastResponse getWeatherForecast(@Argument Long cityId) {
        return weatherService.getWeatherForecast(cityId);
    }
}
