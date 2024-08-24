package com.hubo.gillajabi.weather.infrastructure.job;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.city.infrastructure.persistence.CityRepository;
import com.hubo.gillajabi.crawl.domain.constant.WeatherRedisConstants;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.WeatherCurrentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class WeatherSearchBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CityRepository cityRepository;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    @Bean
    public Job processWeatherJob() {
        return new JobBuilder("processWeatherJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(processWeatherStep())
                .end()
                .build();
    }

    @Bean
    public Step processWeatherStep() {
        return new StepBuilder("processWeatherStep", jobRepository)
                .<City, List<Pair<City, WeatherCurrentDto>>>chunk(10, transactionManager)
                .reader(weatherCityReader())
                .processor(weatherProcessor())
                .writer(weatherWriter())
                .build();
    }

    @Bean
    public ItemReader<City> weatherCityReader() {
        return new RepositoryItemReaderBuilder<City>()
                .name("weatherCityReader")
                .repository(cityRepository)
                .methodName("findAll")
                .pageSize(10)
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<City, List<Pair<City, WeatherCurrentDto>>> weatherProcessor() {
        return city -> {
            List<Pair<City, WeatherCurrentDto>> results = new ArrayList<>();
            LocalDate today = LocalDate.now();

            for (int dayOffset = 0; dayOffset <= 2; dayOffset++) {
                LocalDate targetDate = today.plusDays(dayOffset);
                WeatherCurrentDto summary = processWeatherForDate(city, targetDate);
                results.add(Pair.of(city, summary));
            }

            return results;
        };
    }

    private WeatherCurrentDto processWeatherForDate(City city, LocalDate date) {
        WeatherCurrentDto summary = new WeatherCurrentDto();

        for (int hour = 6; hour <= 23; hour++) {
            String weatherKey = WeatherRedisConstants.makeWeatherKey(city, date, String.format("%02d00", hour));
            Object weatherData = redisTemplate.opsForValue().get(weatherKey);
            if (weatherData != null) {
                WeatherCurrentDto hourlyData = objectMapper.convertValue(weatherData, WeatherCurrentDto.class);
                updateWeatherSummary(summary, hourlyData);
            }
        }

        return summary;
    }

    //TODO 리펙토링
    private void updateWeatherSummary(WeatherCurrentDto summary, WeatherCurrentDto hourlyData) {
        if (hourlyData.getLiveTemperature() != null) {
            if (summary.getLowTemperature() == null || hourlyData.getLiveTemperature() < summary.getLowTemperature()) {
                summary.setLowTemperature(hourlyData.getLiveTemperature());
            }
            if (summary.getHighTemperature() == null || hourlyData.getLiveTemperature() > summary.getHighTemperature()) {
                summary.setHighTemperature(hourlyData.getLiveTemperature());
            }
        }

        if (hourlyData.getSkyCondition() != null) {
            summary.setSkyCondition(hourlyData.getSkyCondition());
        }

        if (hourlyData.getPrecipitationForm() != null) {
            summary.setPrecipitationForm(hourlyData.getPrecipitationForm());
        }

        if (hourlyData.getPrecipitationProbability() != null) {
            if (summary.getPrecipitationProbability() == null ||
                    hourlyData.getPrecipitationProbability() > summary.getPrecipitationProbability()) {
                summary.setPrecipitationProbability(hourlyData.getPrecipitationProbability());
            }
        }

        if (hourlyData.getHumidity() != null) {
            if (summary.getHumidity() == null || hourlyData.getHumidity() > summary.getHumidity()) {
                summary.setHumidity(hourlyData.getHumidity());
            }
        }

        if (hourlyData.getPrecipitationAmount() != null) {
            summary.setPrecipitationAmount((summary.getPrecipitationAmount() == null ? 0 : summary.getPrecipitationAmount())
                    + hourlyData.getPrecipitationAmount());
        }

        if (hourlyData.getSnowfallAmount() != null) {
            summary.setSnowfallAmount((summary.getSnowfallAmount() == null ? 0 : summary.getSnowfallAmount())
                    + hourlyData.getSnowfallAmount());
        }

        if (hourlyData.getWindSpeed() != null) {
            if (summary.getWindSpeed() == null || hourlyData.getWindSpeed() > summary.getWindSpeed()) {
                summary.setWindSpeed(hourlyData.getWindSpeed());
            }
        }
    }

    @Bean
    public ItemWriter<List<Pair<City, WeatherCurrentDto>>> weatherWriter() {
        return items -> {
            for (List<Pair<City, WeatherCurrentDto>> item : items) {
                for (Pair<City, WeatherCurrentDto> pair : item) {
                    City city = pair.getKey();
                    WeatherCurrentDto summary = pair.getValue();
                    LocalDate date = LocalDate.now().plusDays(item.indexOf(pair));
                    String key = WeatherRedisConstants.makeWeatherKey(city, date);
                    try {
                        String value = objectMapper.writeValueAsString(summary);

                        stringRedisTemplate.opsForValue().set(key, value, Duration.ofDays(5));
                    } catch (JsonProcessingException e) {
                        log.error("날씨 직렬화중 문제 발생 - 도시 : "  + city.getName() + ", 몇 일 :  " + date, e);
                    }
                }
            }
        };
    }
};

