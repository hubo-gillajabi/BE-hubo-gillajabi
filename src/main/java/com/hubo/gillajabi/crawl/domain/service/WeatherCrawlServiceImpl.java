package com.hubo.gillajabi.crawl.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubo.gillajabi.crawl.domain.constant.ForecastType;
import com.hubo.gillajabi.crawl.domain.constant.WeatherRedisConstants;
import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.WeatherDto;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiWeatherCurrentResponse;
import com.hubo.gillajabi.crawl.infrastructure.exception.CrawlException;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CityRepository;
import com.hubo.gillajabi.crawl.infrastructure.util.helper.CrawlApiBuilderHelper;
import com.hubo.gillajabi.global.common.dto.ApiProperties;
import com.hubo.gillajabi.crawl.infrastructure.config.WeatherEndpointConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WeatherCrawlServiceImpl implements WeatherCrawlService {

    private static Map<ForecastType, ApiProperties> weatherApiProperties;

    private final WeatherEndpointConfig weatherEndpointConfig;
    private final CrawlApiBuilderHelper crawlApiBuilderHelper;
    private final GenericCrawlerService genericCrawlerService;
    private final CityRepository cityRepository;
    private final StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    private void init() {
        weatherApiProperties = weatherEndpointConfig.getEndPoint();
        validateWeatherApiProperties();
    }

    private void validateWeatherApiProperties() {
        if (weatherApiProperties == null) {
            throw new IllegalStateException("Weather Crawl Service 가 초기화 되지 않았습니다.");
        }
    }

    public void currentCrawl() {
        List<City> cities = cityRepository.findAll();
        cities.forEach(city -> {
            LocalDate date = LocalDate.now();
            try {
                List<ApiWeatherCurrentResponse.Current> currentWeatherData = crawlWeatherData(city);
                processWeatherData(city, date, currentWeatherData);
            } catch (Exception e) {
                log.info("날씨 데이터 수집 중 오류 발생: " + e.getMessage());
            }
        });
    }

    private List<ApiWeatherCurrentResponse.Current> crawlWeatherData(City city) {
        LocalDate targetDate = LocalDate.now();

        GenericCrawlerService.CrawlPageFunction<ApiWeatherCurrentResponse.Current> function = pageNo -> {
            URI uri = crawlApiBuilderHelper.buildUri(weatherApiProperties.get(ForecastType.CURRENT), city.getNx(), city.getNy(), targetDate, pageNo);
            return genericCrawlerService.crawlPage(ApiWeatherCurrentResponse.class, uri);
        };

        return genericCrawlerService.crawlItems(function);
    }

    private void processWeatherData(City city, LocalDate date, List<ApiWeatherCurrentResponse.Current> currentWeatherData) {
        Map<String, WeatherDto> weatherDataByTime = new HashMap<>();
        for (ApiWeatherCurrentResponse.Current current : currentWeatherData) {
            WeatherDto weatherDto = weatherDataByTime.computeIfAbsent(current.getFcstTime(), k -> new WeatherDto());
            weatherDto.updateWeatherDtoFromCurrent(current);
        }

        weatherDataByTime.forEach((baseTime, weatherDto) -> {
            try {
                String key = WeatherRedisConstants.makeWeatherKey(city, date, baseTime);
                String value = objectMapper.writeValueAsString(weatherDto);
                stringRedisTemplate.opsForValue().set(key, value);
            } catch (Exception e) {
                throw new CrawlException(2, "날씨 데이터 저장 오류: " + e.getMessage());
            }
        });
    }

    @Override
    public void weatherMediumTermCrawl() {
        System.out.println("Weather Medium Term Crawl");
    }

    @Override
    public void alertCrawl() {
        System.out.println("Weather Alert Crawl");
    }
}

