package com.hubo.gillajabi.crawl.infrastructure.config;

import com.hubo.gillajabi.global.common.dto.ApiProperties;
import com.hubo.gillajabi.crawl.domain.constant.ForecastType;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "weather")
@Getter
public class WeatherEndpointConfig {

    private final Map<ForecastType, ApiProperties> weatherEndpoints = new EnumMap<>(ForecastType.class);

    public Map<ForecastType, ApiProperties> getEndPoint() {
        return weatherEndpoints;
    }
}
