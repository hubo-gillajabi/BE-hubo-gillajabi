package com.hubo.gillajabi.domain.crawl.config;

import com.hubo.gillajabi.domain.crawl.dto.type.ApiProperties;
import com.hubo.gillajabi.domain.crawl.dto.type.CityType;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
@ConfigurationProperties(prefix = "road")
@Getter
public class RoadProperties {

    private final Map<CityType, ApiProperties> roadEndpoints = new HashMap<>();

    public ApiProperties getEndpoint(CityType city) {
        return roadEndpoints.get(city);
    }
}
