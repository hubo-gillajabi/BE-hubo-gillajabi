package com.hubo.gillajabi.crawl.domain.service;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.repository.CityRepository;
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

    public List<City> saveCity(List<DuruCourseResponse.Course> responseItems) {
        List<City> cities = new ArrayList<>();
        for (DuruCourseResponse.Course item : responseItems) {
            try {
                String provinceName = CrawlResponseParserHelper.parseDuruResponseByProvince(item.getSigun());
                Province province = Province.fromValue(provinceName);
                String cityName = CrawlResponseParserHelper.parseDuruResponseByCity(item.getSigun());

                Optional<City> existingCity = cityRepository.findByNameAndProvince(cityName, province);

                if (existingCity.isEmpty()) {
                    City city = new City(cityName, province);
                    cityRepository.save(city);
                    cities.add(city);
                } else {
                    cities.add(existingCity.get());
                }
            } catch (CrawlException e) {
                log.error("CityService.saveCity 실행중 문제 발생: {}", e.getMessage());
            }
        }
        return cities;
    }
}
