package com.hubo.gillajabi.crawl.domain.service.duru;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CityRepository;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CityRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.exception.CrawlException;
import com.hubo.gillajabi.crawl.infrastructure.util.helper.CrawlResponseParserHelper;
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
public class CityDuruService {

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
            final CityRequestDTO cityRequestDTO = createCityRequestDTOFromCourseItem(item);
            final City city = createOrFindCity(cityRequestDTO);
            cities.add(city);
        } catch (final CrawlException e) {
            log.error("CityService.saveCity 실행중 문제 발생: {}", e.getMessage());
        }
    }

    private CityRequestDTO createCityRequestDTOFromCourseItem(final DuruCourseResponse.Course item) {
        final String provinceName = CrawlResponseParserHelper.parseDuruResponseByProvince(item.getSigun());
        final Province province = Province.fromValue(provinceName);
        final String cityName = CrawlResponseParserHelper.parseDuruResponseByCity(item.getSigun());

        return CityRequestDTO.of(cityName, province);
    }

    private City createOrFindCity(final CityRequestDTO cityRequestDTO) {
        return cityRepository.findByNameAndProvince(cityRequestDTO.getName(), cityRequestDTO.getProvince())
                .orElseGet(() -> createAndSaveCity(cityRequestDTO));
    }

    private City createAndSaveCity(final CityRequestDTO cityRequestDTO) {
        final City city = City.createCity(cityRequestDTO);
        cityRepository.save(city);
        return city;
    }
}
