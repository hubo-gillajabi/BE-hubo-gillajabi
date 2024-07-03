package com.hubo.gillajabi.crawl.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubo.gillajabi.crawl.domain.constant.ForecastType;
import com.hubo.gillajabi.crawl.domain.constant.WeatherRedisConstants;
import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.WeatherCurrentDto;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.WeatherTermDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.AbstractApiResponse;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiWeatherCurrentResponse;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiWeatherMediumTermResponse;
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
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WeatherCrawlServiceImpl implements WeatherCrawlService {

    private static Map<ForecastType, ApiProperties> weatherApiProperties;

    private final WeatherEndpointConfig weatherEndpointConfig;
    private final CrawlApiBuilderHelper crawlApiBuilderHelper;
    private final PrimaryCrawlingService primaryCrawlingService;
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

    @Override
    public void currentCrawl() {
        List<City> cities = cityRepository.findAll();
        cities.forEach(city -> {
            try {
                List<ApiWeatherCurrentResponse.Current> currentWeatherData = crawlWeatherData(city, LocalDate.now());
                processWeatherData(city, currentWeatherData);
            } catch (Exception e) {
                log.info("날씨 데이터 수집 중 오류 발생: " + e.getMessage());
            }
        });
    }

    private List<ApiWeatherCurrentResponse.Current> crawlWeatherData(City city, LocalDate targetDate) {
        return primaryCrawlingService.crawlItems(pageNo -> {
            URI uri = crawlApiBuilderHelper.buildUri(weatherApiProperties.get(ForecastType.CURRENT), city.getNx(), city.getNy(), targetDate, pageNo);
            return primaryCrawlingService.crawlPage(ApiWeatherCurrentResponse.class, uri);
        });
    }

    private void processWeatherData(City city, List<ApiWeatherCurrentResponse.Current> currentWeatherData) {
        Map<String, WeatherCurrentDto> weatherDataByTime = new HashMap<>();
        Map<String, WeatherCurrentDto> dailyWeatherData = new HashMap<>();

        for (ApiWeatherCurrentResponse.Current current : currentWeatherData) {
            String dateTimeKey = current.getFcstDate() + current.getFcstTime();
            WeatherCurrentDto weatherCurrentDto = weatherDataByTime.computeIfAbsent(dateTimeKey, k -> new WeatherCurrentDto());
            weatherCurrentDto.updateWeatherDtoFromCurrent(current);

            WeatherCurrentDto dailyWeatherCurrentDto = dailyWeatherData.computeIfAbsent(current.getFcstDate(), k -> new WeatherCurrentDto());

            updateTemperatureData(current, dailyWeatherCurrentDto);
        }

        saveWeatherDataToRedis(city, weatherDataByTime, dailyWeatherData);
    }

    /**
     * 현재 날씨 데이터에서 최저/최고 기온 데이터를 추출하여 WeatherCurrentDto에 저장
     * 최저기온 : 오전 6시, 최고기온 : 오후 3시
     * @param current : response
     * @param dailyWeatherCurrentDto : 날짜별 날씨 데이터
     */
    private void updateTemperatureData(ApiWeatherCurrentResponse.Current current, WeatherCurrentDto dailyWeatherCurrentDto) {
        if ("TMN".equals(current.getCategory()) && "0600".equals(current.getFcstTime())) {
            dailyWeatherCurrentDto.setLowTemperature(Float.parseFloat(current.getFcstValue()));
        }
        if ("TMX".equals(current.getCategory()) && "1500".equals(current.getFcstTime())) {
            dailyWeatherCurrentDto.setHighTemperature(Float.parseFloat(current.getFcstValue()));
        }
    }

    private void saveWeatherDataToRedis(City city, Map<String, WeatherCurrentDto> weatherDataByTime, Map<String, WeatherCurrentDto> dailyWeatherData) {
        weatherDataByTime.forEach((dateTimeKey, weatherCurrentDto) -> {
            saveWeatherData(city, dateTimeKey, weatherCurrentDto);
        });

        dailyWeatherData.forEach((fcstDate, dailyWeatherCurrentDto) -> {
            saveDailyWeatherData(city, fcstDate, dailyWeatherCurrentDto);
        });
    }

    private void saveWeatherData(City city, String dateTimeKey, WeatherCurrentDto weatherCurrentDto) {
        try {
            String date = dateTimeKey.substring(0, 8);
            String time = dateTimeKey.substring(8);
            LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
            String key = WeatherRedisConstants.makeWeatherKey(city, parsedDate, time);
            String value = objectMapper.writeValueAsString(weatherCurrentDto);
            stringRedisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            throw new CrawlException(4, "날씨 데이터 저장 오류: " + e.getMessage());
        }
    }

    private void saveDailyWeatherData(City city, String fcstDate, WeatherCurrentDto dailyWeatherCurrentDto) {
        try {
            LocalDate parsedDate = LocalDate.parse(fcstDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
            String dailyKey = WeatherRedisConstants.makeWeatherKey(city, parsedDate, null);
            String dailyValue = objectMapper.writeValueAsString(dailyWeatherCurrentDto);
            stringRedisTemplate.opsForValue().set(dailyKey, dailyValue);
        } catch (Exception e) {
            throw new CrawlException(4, "날씨 데이터 저장 오류: " + e.getMessage());
        }
    }

    @Override
    public void weatherMediumTermCrawl() {
        List<City> cities = cityRepository.findAll();
        cities.forEach(city -> {
            LocalDate date = LocalDate.now();
            try {
                ApiWeatherMediumTermResponse.Detail mediumTermWeatherDetailData = crawlMediumTermWeatherDetailData(date, city);
                ApiWeatherMediumTermResponse.Temperature mediumTermWeatherTemperatureData = crawlMediumTermWeatherTemperatureData(date, city);

                processMediumTermWeatherData(city, date, mediumTermWeatherDetailData, mediumTermWeatherTemperatureData);
            } catch (Exception e) {
                log.info("중기 날씨 데이터 수집 중 오류 발생: " + e.getMessage());
            }
        });
    }

    private ApiWeatherMediumTermResponse.Temperature crawlMediumTermWeatherTemperatureData(LocalDate date, City city) {
        URI weatherTemperatureURI = crawlApiBuilderHelper.buildUri("getMidTa", weatherApiProperties.get(ForecastType.MEDIUM_TERM), city, 1, date);
        AbstractApiResponse<ApiWeatherMediumTermResponse.Temperature> response = primaryCrawlingService.crawlOnePage(ApiWeatherMediumTermResponse.Temperature.class, weatherTemperatureURI);
        return response.getResponse().getBody().getItems().getItem().get(0);
    }

    private ApiWeatherMediumTermResponse.Detail crawlMediumTermWeatherDetailData(LocalDate date, City city) {
        URI weatherDetailURI = crawlApiBuilderHelper.buildUri("getMidLandFcst", weatherApiProperties.get(ForecastType.MEDIUM_TERM), city, 1, date);
        AbstractApiResponse<ApiWeatherMediumTermResponse.Detail> response = primaryCrawlingService.crawlOnePage(ApiWeatherMediumTermResponse.Detail.class, weatherDetailURI);
        return response.getResponse().getBody().getItems().getItem().get(0);
    }

    private void processMediumTermWeatherData(City city, LocalDate date, ApiWeatherMediumTermResponse.Detail mediumTermWeatherDetailData, ApiWeatherMediumTermResponse.Temperature mediumTermWeatherTemperatureData) {
        final LocalDate makeKeyDate = date.plusDays(3);
        final WeatherTermDTO weatherTermDTO = WeatherTermDTO.of(mediumTermWeatherDetailData, mediumTermWeatherTemperatureData);
        try {
            String key = WeatherRedisConstants.makeWeatherKey(city, makeKeyDate);
            String value = objectMapper.writeValueAsString(weatherTermDTO);
            stringRedisTemplate.opsForValue().set(key, value);

        } catch (Exception e) {
            throw new CrawlException(4, "중기 날씨 데이터 저장 오류: " + e.getMessage());
        }
    }

    @Override
    public void alertCrawl() {
        //TODO: 예보 구현 필요
    }
}

