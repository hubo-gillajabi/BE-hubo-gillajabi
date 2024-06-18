package com.hubo.gillajabi.crawl.infrastructure.config;

import com.hubo.gillajabi.crawl.domain.constant.CityName;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
@ConfigurationProperties(prefix = "road")
@Slf4j
@Getter
public class RoadProperties {

    private final Map<CityName, ApiProperties> roadEndpoints = new HashMap<>();

    public ApiProperties getEndpoint(CityName cityName) {
        return roadEndpoints.get(cityName);
    }
}
