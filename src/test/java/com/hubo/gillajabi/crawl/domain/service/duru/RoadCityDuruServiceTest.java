package com.hubo.gillajabi.crawl.domain.service.duru;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;
import com.hubo.gillajabi.city.infrastructure.persistence.CityRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoadCityDuruServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private RoadCityDuruService roadCityDuruService;

    private final FixtureMonkey fixtureMonkey = FixtureMonkey.create();

    public void findByNameAndProvince() {
        when(cityRepository.findByNameAndProvince("김해시", Province.fromValue("경남")))
                .thenReturn(Optional.empty());
    }

    @DisplayName("DuruCourseResponse.Course를 받아서 새로운 city객체를 생성하고 저장")
    @Test
    void DuruCourseResponse_Course를_받아서_새로운_city객체를_생성하고_저장() {
        // given
        List<ApiCourseResponse.Course> responseItems = fixtureMonkey.giveMeBuilder(ApiCourseResponse.Course.class)
                .set("sigun", "경남 김해시")
                .sampleList(1);

        findByNameAndProvince();

        // when
        List<City> savedCities = roadCityDuruService.saveCity(responseItems);

        // then
        assertEquals(1, savedCities.size());
        verify(cityRepository, times(1)).save(any(City.class));
    }
}
