package com.hubo.gillajabi.search.infrastructure.job;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.city.infrastructure.persistence.CityRepository;
import com.hubo.gillajabi.crawl.domain.constant.WeatherRedisConstants;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseTag;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.WeatherCurrentDto;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import com.hubo.gillajabi.search.domain.document.CourseSearchDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


//TODO 리펙토링 필요 (일단 기능만 짜둔거)
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class CourseSearchBatchConfig {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    private final CityRepository cityRepository;

    private final  ElasticsearchOperations elasticsearchOperations;

    private final CourseRepository courseRepository;

    private final ObjectMapper objectMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    @Bean
    public Job courseSearchJob() {
        return new JobBuilder("courseSearchJob", jobRepository)
                .start(citySearchStep())
                .next(courseSearchStep())
                .build();
    }

    @Bean
    public Step citySearchStep() {
        return new StepBuilder("citySearchStep", jobRepository)
                .<City, CourseSearchDocument>chunk(100, transactionManager)
                .reader(cityReader())
                .processor(cityProcessor())
                .writer(singleDocumentWriter())
                .build();
    }

    @Bean
    public Step courseSearchStep() {
        return new StepBuilder("courseSearchStep", jobRepository)
                .<Course, CourseSearchDocument>chunk(100, transactionManager)
                .reader(courseReader())
                .processor(courseProcessor())
                .writer(courseSearchWriter())
                .build();
    }

    @Bean
    public ItemReader<City> cityReader() {
        return new RepositoryItemReaderBuilder<City>()
                .name("cityReader")
                .repository(cityRepository)
                .methodName("findAll")
                .pageSize(100)
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemReader<Course> courseReader() {
        return new RepositoryItemReaderBuilder<Course>()
                .name("courseReader")
                .repository(courseRepository)
                .methodName("findAll")
                .pageSize(100)
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<? super City, ? extends CourseSearchDocument> cityProcessor() {
        return city -> {
            String cityName = null;
            if(city.getProvince().isBigCity()){
                cityName = city.getProvince().getValue();
            }
            else{
                cityName = city.getName();
            }

            Set<Course> courses = city.getCourses();
            Set<CourseTheme> uniqueThemes = courses.stream()
                    .map(Course::getCourseTheme)
                    .collect(Collectors.toSet());

            CourseSearchDocument cityDocument = new CourseSearchDocument();
            cityDocument.setId("city_" + cityName);
            cityDocument.setCity(mapCity(city));
            cityDocument.setThemes(uniqueThemes.stream().map(this::mapTheme).collect(Collectors.toList()));
            cityDocument.setImages(getImagesForCity(city));
            cityDocument.setWeather(getWeatherForCity(city));
            cityDocument.setTags(getTagsForCity(city));

            return cityDocument;
        };
    }

    @Bean
    public ItemWriter<CourseSearchDocument> courseSearchWriter() {
        return items -> {
            List<IndexQuery> queries = new ArrayList<>();
            for (CourseSearchDocument document : items) {
                if (document != null) {
                    queries.add(new IndexQueryBuilder()
                            .withId(document.getId())
                            .withObject(document)
                            .build());
                }
            }
            if (!queries.isEmpty()) {
                elasticsearchOperations.bulkIndex(queries, CourseSearchDocument.class);
            }
        };
    }


    @Bean
    public ItemProcessor<Course, CourseSearchDocument> courseProcessor() {
        Set<String> processedCombinations = new HashSet<>();

        return course -> {
            CourseTheme theme = course.getCourseTheme();
            City city = course.getCity();

            String cityName = null;
            if(city.getProvince().isBigCity()){
                cityName = city.getProvince().getValue();
            }
            else{
                cityName = city.getName();
            }

            String combinationKey = "theme_" + theme.getName() + "_" + cityName;

            if (processedCombinations.contains(combinationKey)) {
                return null; // 이미 처리된 조합은 패스
            }

            processedCombinations.add(combinationKey);

            CourseSearchDocument document = new CourseSearchDocument();
            document.setId(combinationKey);
            document.setCity(mapCity(city));
            document.setTheme(mapTheme(theme));
            document.setImages(getImagesForTheme(theme, course));
            document.setWeather(getWeatherForCity(city));
            document.setTags(getTagsForTheme(theme));

            return document;
        };
    }


    @Bean
    public ItemWriter<CourseSearchDocument> singleDocumentWriter() {
        return items -> {
            List<IndexQuery> queries = new ArrayList<>();
            for (CourseSearchDocument document : items) {
                queries.add(new IndexQueryBuilder()
                        .withId(document.getId())
                        .withObject(document)
                        .build());
            }
            if (!queries.isEmpty()) {
                elasticsearchOperations.bulkIndex(queries, CourseSearchDocument.class);
            }
        };
    }


    @Bean
    public ItemWriter<List<CourseSearchDocument>> multipleDocumentsWriter() {
        return items -> {
            List<IndexQuery> queries = new ArrayList<>();
            for (List<CourseSearchDocument> documentList : items) {
                for (CourseSearchDocument document : documentList) {
                    queries.add(new IndexQueryBuilder()
                            .withId(document.getId())
                            .withObject(document)
                            .build());
                }
            }
            if (!queries.isEmpty()) {
                elasticsearchOperations.bulkIndex(queries, CourseSearchDocument.class);
            }
        };
    }

    private CourseSearchDocument.City mapCity(City city) {
        return CourseSearchDocument.City.builder()
                .id(city.getId())
                .name(city.getName())
                .province(city.getProvince().getValue())
                .build();
    }

    private CourseSearchDocument.Theme mapTheme(CourseTheme courseTheme) {
        return CourseSearchDocument.Theme.builder()
                .id(courseTheme.getId())
                .name(courseTheme.getName())
                .shortDescription(courseTheme.getShortDescription())
                .build();
    }

    // TODO: 이미지
    private List<String> getImagesForCity(City city) {
        // TODO : 도시 이미지 조회 로직
        return new ArrayList<>();
    }

    private List<String> getImagesForTheme(CourseTheme theme, Course course) {
        //TODO: 테마와 도시에 대한 이미지 조회 로직
        return new ArrayList<>();
    }

    private CourseSearchDocument.WeatherInfo getWeatherForCity(City city) {
        // TODO 날씨 정보 조회 로직
        String temperatureKey = WeatherRedisConstants.makeWeatherKey(city, LocalDate.now());
        Object temperatureObject = redisTemplate.opsForValue().get(temperatureKey);

        if (temperatureObject != null ) {
            WeatherCurrentDto temperatureDto = objectMapper.convertValue(temperatureObject, WeatherCurrentDto.class);

            return CourseSearchDocument.WeatherInfo.builder()
                    .lowestTemperature(temperatureDto.getLowTemperature())
                    .highestTemperature(temperatureDto.getHighTemperature())
                    .skyCondition(temperatureDto.getSkyCondition())
                    .precipitationForm(temperatureDto.getPrecipitationForm())
                    .build();
        }
        return null;
    }

    private List<String> getTagsForCity(City city) {
        // TODO 도시 태그 조회 로직
        return new ArrayList<>();
    }

    private List<String> getTagsForTheme(CourseTheme theme) {

        return theme.getCourseTags().stream()
                .map(CourseTag::getName)
                .toList();
    }
}