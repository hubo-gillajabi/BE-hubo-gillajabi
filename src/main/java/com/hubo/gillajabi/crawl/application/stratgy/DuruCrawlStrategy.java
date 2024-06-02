package com.hubo.gillajabi.crawl.application.stratgy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubo.gillajabi.crawl.domain.constant.CityName;
import com.hubo.gillajabi.crawl.domain.constant.CrawlType;
import com.hubo.gillajabi.crawl.domain.entity.CrawlApiResponse;
import com.hubo.gillajabi.crawl.domain.service.ApiResponseService;
import com.hubo.gillajabi.crawl.infrastructure.util.helper.CrawlApiBuilderHelper;
import com.hubo.gillajabi.crawl.infrastructure.config.RoadProperties;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruThemeResponse;
import com.hubo.gillajabi.crawl.domain.constant.ApiProperties;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ValidatableResponse;
import com.hubo.gillajabi.crawl.infrastructure.exception.CrawlException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DuruCrawlStrategy implements CrawlStrategy {

    private final RestTemplate restTemplate;
    private final RoadProperties roadProperties;
    private final CrawlApiBuilderHelper crawlApiBuilderHelper;
    private final ApiResponseService apiResponseService;

    private ApiProperties duruApiProperties;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final int numOfRows = 100;

    @PostConstruct
    private void init() {
        this.duruApiProperties = roadProperties.getEndpoint(CityName.DURU);
        if (this.duruApiProperties == null) {
            throw new IllegalStateException("DURU Crawl Strategy 가 초기화 되지 않았습니다.");
        }
    }

    public List<DuruCourseResponse.Course> crawlCourse() {
        return crawlItems(this::crawlCoursePage, CrawlType.COURSE);
    }

    public List<DuruThemeResponse.Theme> crawlTheme() {
        List<DuruThemeResponse.Theme> themes = crawlItems(this::crawlThemePage, CrawlType.THEME);
        themes.forEach(theme -> theme.setThemedescs(Jsoup.parse(theme.getThemedescs()).text()));
        return themes;
    }

    private <T> List<T> crawlItems(CrawlPageFunction<T> crawlPageFunction, CrawlType itemType){
        int pageNo = 1;
        List<T> allItems = new ArrayList<>();

        while (true) {
            try {
                List<T> items = crawlPageFunction.crawlPage(pageNo);
                if (items.isEmpty()) {
                    break;
                }

                log.info("두루누비 {} 크롤링 중: pageNo={}, items={}", itemType, pageNo, items.size());
                allItems.addAll(items);

                pageNo++;
            } catch (Exception e) {
                log.error("크롤링 중 예기치 못한 오류 발생: ", e);
                throw new CrawlException(500, "크롤링 중 예기치 못한 오류 발생");
            }
        }
        return allItems;
    }

    private List<DuruCourseResponse.Course> crawlCoursePage(int pageNo) throws JsonProcessingException {
        DuruCourseResponse duruCourseResponse = crawlPage(pageNo, "courseList", DuruCourseResponse.class);
        DuruCourseResponse.Body body = duruCourseResponse.getResponse().getBody();
        if (body == null || body.getItems() == null || body.getItems().getItem() == null) {
            log.warn("해당 페이지를 찾을 수 없음 : {}", pageNo);
            return new ArrayList<>(); // 빈 리스트 반환
        }
        return body.getItems().getItem();
    }

    private List<DuruThemeResponse.Theme> crawlThemePage(int pageNo) throws JsonProcessingException {
        DuruThemeResponse duruThemeResponse = crawlPage(pageNo, "routeList", DuruThemeResponse.class);
        DuruThemeResponse.Body body = duruThemeResponse.getResponse().getBody();
        if (body == null || body.getItems() == null || body.getItems().getItem() == null) {
            log.warn("해당 페이지를 찾을 수 없음 : {}", pageNo);
            return new ArrayList<>();
        }
        return body.getItems().getItem();
    }

    private <T extends ValidatableResponse> T crawlPage(int pageNo, String endpoint, Class<T> responseType) throws JsonProcessingException {
        URI uri = buildUri(endpoint, pageNo);
        String response = fetchApiResponse(uri);
        log.info("두루누비 API 호출 결과: {}", response);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        T parsedResponse = objectMapper.readValue(response, responseType);
        crawlApiBuilderHelper.validateResponse(parsedResponse);
        return parsedResponse;
    }

    private URI buildUri(String endpoint, int pageNo) {
        return crawlApiBuilderHelper.buildUri(endpoint, duruApiProperties, pageNo, numOfRows);
    }

    private String fetchApiResponse(URI uri) {
        Optional<CrawlApiResponse> cachedResponse = apiResponseService.findByRequestUrl(uri.toString());
        if (cachedResponse.isPresent()) {
            return cachedResponse.get().getResponse();
        } else {
            try {
                String response = restTemplate.getForObject(uri, String.class);
                apiResponseService.saveApiResponse(uri.toString(), response);
                return response;
            } catch (Exception e) {
                throw new RuntimeException("crawl response를 저장하는 중에 문제 발생", e);
            }
        }
    }

    @FunctionalInterface
    private interface CrawlPageFunction<T> {
        List<T> crawlPage(int pageNo) throws JsonProcessingException;
    }
}
