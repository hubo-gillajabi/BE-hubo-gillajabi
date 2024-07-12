package com.hubo.gillajabi.crawl.domain.service.duru;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.city.infrastructure.persistence.CityRepository;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CityRequest;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.exception.CrawlException;
import com.hubo.gillajabi.crawl.infrastructure.util.helper.RoadCrawlResponseParserHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RoadCityDuruService {

    private final CityRepository cityRepository;

    public List<City> saveCity(final List<ApiCourseResponse.Course> responseItems) {
        List<City> cities = new ArrayList<>();
        for (ApiCourseResponse.Course item : responseItems) {
            processCourseItem(cities, item);
        }
        return cities;
    }

    private void processCourseItem(final List<City> cities, final ApiCourseResponse.Course item) {
        try {
            final CityRequest cityRequest = createCityRequestDTOFromCourseItem(item);
            final City city = createOrFindCity(cityRequest);
            cities.add(city);
        } catch (final CrawlException e) {
            log.error("CityService.saveCity 실행중 문제 발생: " + e.getMessage());
        }
    }

    private CityRequest createCityRequestDTOFromCourseItem(final ApiCourseResponse.Course item) {
        final String provinceName = RoadCrawlResponseParserHelper.parseDuruResponseByProvince(item.getSigun());
        final Province province = Province.fromValue(provinceName);
        final String cityName = RoadCrawlResponseParserHelper.parseDuruResponseByCity(item.getSigun());

        return CityRequest.of(cityName, province);
    }

    private City createOrFindCity(final CityRequest cityRequest) {
        return cityRepository.findByNameAndProvince(cityRequest.getName(), cityRequest.getProvince())
                .orElseGet(() -> createAndSaveCity(cityRequest));
    }

    private City createAndSaveCity(final CityRequest cityRequest) {
        final City city = City.createCity(cityRequest);
        cityRepository.save(city);
        return city;
    }
}
