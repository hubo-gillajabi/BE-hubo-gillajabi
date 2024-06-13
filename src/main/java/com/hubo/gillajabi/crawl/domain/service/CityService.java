package com.hubo.gillajabi.crawl.domain.service;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CityRepository;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.exception.CrawlException;
import com.hubo.gillajabi.crawl.infrastructure.util.helper.CrawlResponseParserHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CityService {

    private final CityRepository cityRepository;

    public List<City> saveCity(final List<DuruCourseResponse.Course> responseItems) {
        List<City> cities = new ArrayList<>();
        for (DuruCourseResponse.Course item : responseItems) {
            processCourseItem(cities, item);
        }
        return cities;
    }

    private void processCourseItem(final List<City> cities, final DuruCourseResponse.Course item) {
        try {
            final City city = createCityFromCourseItem(item);
            cities.add(city);
        } catch (final CrawlException e) {
            log.error("CityService.saveCity 실행중 문제 발생: {}", e.getMessage());
        }
    }

    private City createCityFromCourseItem(final DuruCourseResponse.Course item) {
        final String provinceName = CrawlResponseParserHelper.parseDuruResponseByProvince(item.getSigun());
        final Province province = Province.fromValue(provinceName);
        final String cityName = CrawlResponseParserHelper.parseDuruResponseByCity(item.getSigun());

        return createOrFindCity(cityName, province);
    }

    private City createOrFindCity(final String cityName, final Province province) {
        return cityRepository.findByNameAndProvince(cityName, province)
                .orElseGet(() -> createAndSaveCity(cityName, province));
    }

    private City createAndSaveCity(final String cityName, final Province province) {
        final City city = City.of(cityName, province);
        cityRepository.save(city);
        return city;
    }
}

