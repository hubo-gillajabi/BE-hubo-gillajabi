package com.hubo.gillajabi.crawl.domain.entity;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CityRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    @Test
    @DisplayName("createCity 메서드는 CityRequest를 사용하여 City 객체를 생성한다")
    public void createCity_유효한_CityRequest로_City_객체_생성() {
        // given
        CityRequest cityRequest = CityRequest.of("Seoul", Province.SEOUL, "서울");

        // when
        City city = City.createCity(cityRequest);

        // then
        assertEquals("Seoul", city.getName());
        assertEquals(Province.SEOUL, city.getProvince());
        assertEquals("서울", city.getDescription());
    }
}
