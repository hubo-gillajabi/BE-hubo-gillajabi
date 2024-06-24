package com.hubo.gillajabi.road.infraStructure.config;

import com.hubo.gillajabi.crawl.domain.constant.CityName;
import com.hubo.gillajabi.global.common.dto.ApiProperties;
import com.hubo.gillajabi.crawl.infrastructure.config.RoadEndpointConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class RoadEndpointConfigTest {

    @Autowired
    private RoadEndpointConfig roadEndpointConfig;

    @Test
    @DisplayName("road-yml에 정의된 road를 올바르게 읽어온다.")
    void testRoadProperties() {
        ApiProperties duruProperties = roadEndpointConfig.getEndpoint(CityName.DURU);
        assertThat(duruProperties).isNotNull();
        assertThat(duruProperties.getEndpoint()).isEqualTo("http://example.com");
        assertThat(duruProperties.getEncoding()).isEqualTo("serviceKey");
        assertThat(duruProperties.getDecoding()).isEqualTo("serviceKey");
        assertThat(duruProperties.getSiteUrl()).isEqualTo("http://example.com");

        ApiProperties busanProperties = roadEndpointConfig.getEndpoint(CityName.BUSAN);
        assertThat(busanProperties).isNotNull();
        assertThat(busanProperties.getEndpoint()).isEqualTo("http://example.com");
        assertThat(busanProperties.getEncoding()).isEqualTo("serviceKey");
        assertThat(busanProperties.getDecoding()).isEqualTo("serviceKey");
        assertThat(busanProperties.getSiteUrl()).isEqualTo("http://example.com");
    }
}