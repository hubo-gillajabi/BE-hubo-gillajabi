package com.hubo.gillajabi.crawl.infrastructure.config;

import com.hubo.gillajabi.global.common.dto.ApiProperties;
import com.hubo.gillajabi.crawl.domain.constant.CityName;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;


@Configuration
@ConfigurationProperties(prefix = "road")
@Slf4j
@Getter
public class RoadEndpointConfig {

    private final Map<CityName, ApiProperties> roadEndpoints = new EnumMap<>(CityName.class);

    public ApiProperties getEndpoint(CityName cityName) {
        return roadEndpoints.get(cityName);
    }
}
