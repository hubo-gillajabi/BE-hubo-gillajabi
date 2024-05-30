package com.hubo.gillajabi.crawl.application.stratgy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubo.gillajabi.crawl.domain.constant.CityName;
import com.hubo.gillajabi.crawl.domain.constant.CrawlType;
import com.hubo.gillajabi.crawl.infrastructure.Helper.ApiHelper;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class DuruCrawlStrategy implements CrawlStrategy {

    private final RestTemplate restTemplate;
    private final RoadProperties roadProperties;
    private final ApiHelper apiHelper;

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
                if (isEndOfItems(items)) {
                    break;
                }

                log.info("두루누비 {} 크롤링 중: pageNo={}, items={}", itemType, pageNo, items.size());
                allItems.addAll(items);

                if (isLastPage(pageNo, items.size())) {
                    break;
                }

                pageNo++;
            } catch (Exception e) {
                log.error("크롤링 중 예기치 못한 오류 발생: ", e);
                throw new CrawlException(500, "크롤링 중 예기치 못한 오류 발생");
            }
        }
        return allItems;
    }

    private List<DuruCourseResponse.Course> crawlCoursePage(int pageNo) throws JsonProcessingException {
        return crawlPage(pageNo, "courseList", DuruCourseResponse.class).getResponse().getBody().getItems().getItem();
    }

    private List<DuruThemeResponse.Theme> crawlThemePage(int pageNo) throws JsonProcessingException {
        return crawlPage(pageNo, "routeList", DuruThemeResponse.class).getResponse().getBody().getItems().getItem();
    }

    private <T extends ValidatableResponse> T crawlPage(int pageNo, String endpoint, Class<T> responseType) throws JsonProcessingException {
        URI uri = apiHelper.buildUri(endpoint, duruApiProperties, pageNo, numOfRows);
        String response = restTemplate.getForObject(uri, String.class);
        T parsedResponse = objectMapper.readValue(response, responseType);
        apiHelper.validateResponse(parsedResponse);
        return parsedResponse;
    }

    private boolean isEndOfItems(List<?> items) {
        return items == null || items.isEmpty();
    }

    private boolean isLastPage(int pageNo, int itemCount) {
        return pageNo * numOfRows >= itemCount;
    }

    @FunctionalInterface
    private interface CrawlPageFunction<T> {
        List<T> crawlPage(int pageNo) throws JsonProcessingException;
    }
}
