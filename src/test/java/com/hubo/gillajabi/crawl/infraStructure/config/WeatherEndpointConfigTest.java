package com.hubo.gillajabi.crawl.infraStructure.config;

import com.hubo.gillajabi.crawl.domain.constant.ForecastType;
import com.hubo.gillajabi.crawl.infrastructure.config.WeatherEndpointConfig;
import com.hubo.gillajabi.global.common.dto.ApiProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EnableConfigurationProperties(WeatherEndpointConfig.class)
@ActiveProfiles("test")
class WeatherEndpointConfigTest {

    @Autowired
    private WeatherEndpointConfig weatherEndpointConfig;

    @Test
    public void testCurrentWeatherEndpointConfig() {
        ApiProperties currentProperties = weatherEndpointConfig.getEndPoint().get(ForecastType.CURRENT);
        assertNotNull(currentProperties);
        assertEquals("http://example.com", currentProperties.getEndpoint());
        assertEquals("serviceKey", currentProperties.getEncoding());
        assertEquals("serviceKey", currentProperties.getDecoding());
        assertEquals("http://example.com", currentProperties.getSiteUrl());
    }

    @Test
    public void testWeatherAlertEndpointConfig() {
        ApiProperties alertProperties = weatherEndpointConfig.getEndPoint().get(ForecastType.WEATHER_ALERT);
        assertNotNull(alertProperties);
        assertEquals("http://example.com", alertProperties.getEndpoint());
        assertEquals("serviceKey", alertProperties.getEncoding());
        assertEquals("serviceKey", alertProperties.getDecoding());
        assertEquals("http://example.com", alertProperties.getSiteUrl());
    }

    @Test
    public void testMediumTermWeatherEndpointConfig() {
        ApiProperties mediumTermProperties = weatherEndpointConfig.getEndPoint().get(ForecastType.MEDIUM_TERM);
        assertNotNull(mediumTermProperties);
        assertEquals("http://example.com", mediumTermProperties.getEndpoint());
        assertEquals("serviceKey", mediumTermProperties.getEncoding());
        assertEquals("serviceKey", mediumTermProperties.getDecoding());
        assertEquals("http://example.com", mediumTermProperties.getSiteUrl());
    }


}
