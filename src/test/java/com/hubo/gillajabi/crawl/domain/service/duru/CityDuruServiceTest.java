package com.hubo.gillajabi.crawl.domain.service.duru;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CityRepository;
import com.hubo.gillajabi.crawl.infrastructure.util.helper.CrawlResponseParserHelper;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityDuruServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityDuruService cityDuruService;

    private final FixtureMonkey fixtureMonkey = FixtureMonkey.create();

    public void findByNameAndProvince() {
        when(cityRepository.findByNameAndProvince("김해시", Province.fromValue("경남")))
                .thenReturn(Optional.empty());
    }

    @DisplayName("DuruCourseResponse.Course를 받아서 새로운 city객체를 생성하고 저장")
    @Test
    void DuruCourseResponse_Course를_받아서_새로운_city객체를_생성하고_저장() {
        // given
        List<DuruCourseResponse.Course> responseItems = fixtureMonkey.giveMeBuilder(DuruCourseResponse.Course.class)
                .set("sigun", "경남 김해시")
                .sampleList(1);

        findByNameAndProvince();

        // when
        List<City> savedCities = cityDuruService.saveCity(responseItems);

        // then
        assertEquals(1, savedCities.size());
        verify(cityRepository, times(1)).save(any(City.class));
    }
}
