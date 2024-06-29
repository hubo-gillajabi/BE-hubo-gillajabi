package com.hubo.gillajabi.crawl.infrastructure.config;

import com.hubo.gillajabi.global.common.dto.ApiProperties;
import com.hubo.gillajabi.crawl.domain.constant.CityCrawlName;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;


@Configuration
@ConfigurationProperties(prefix = "road")
@Getter
public class RoadEndpointConfig {

    private final Map<CityCrawlName, ApiProperties> roadEndpoints = new EnumMap<>(CityCrawlName.class);

    public ApiProperties getEndpoint(CityCrawlName cityCrawlName) {
        return roadEndpoints.get(cityCrawlName);
    }
}
