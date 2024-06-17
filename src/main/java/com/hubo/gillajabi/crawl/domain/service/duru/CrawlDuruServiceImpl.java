package com.hubo.gillajabi.crawl.domain.service.duru;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubo.gillajabi.crawl.domain.constant.CityName;
import com.hubo.gillajabi.crawl.domain.service.AbstarctCrawlService;
import com.hubo.gillajabi.crawl.domain.service.ApiResponseService;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.AbstractDuruResponse;
import com.hubo.gillajabi.crawl.infrastructure.util.helper.CrawlApiBuilderHelper;
import com.hubo.gillajabi.crawl.infrastructure.config.RoadProperties;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruThemeResponse;
import com.hubo.gillajabi.crawl.infrastructure.config.ApiProperties;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ValidatableResponse;
import com.hubo.gillajabi.crawl.infrastructure.exception.CrawlException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlDuruServiceImpl extends AbstarctCrawlService {

    private final RoadProperties roadProperties;
    private final CrawlApiBuilderHelper crawlApiBuilderHelper;
    private final ApiResponseService apiResponseService;

    private ApiProperties duruApiProperties;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final int numOfRows = 100;

    @PostConstruct
    private void init() {
        this.duruApiProperties = roadProperties.getEndpoint(CityName.DURU);
        validateDuruApiProperties();
    }

    private void validateDuruApiProperties() {
        if (this.duruApiProperties == null) {
            throw new IllegalStateException("DURU Crawl Strategy 가 초기화 되지 않았습니다.");
        }
    }

    @Override
    public List<DuruCourseResponse.Course> crawlCourse() {
        return crawlItems(this::crawlCoursePage);
    }

    @Override
    public List<DuruThemeResponse.Theme> crawlTheme() {
        List<DuruThemeResponse.Theme> themes = crawlItems(this::crawlThemePage);
        themes.forEach(theme -> theme.setThemedescs(Jsoup.parse(theme.getThemedescs()).text()));
        return themes;
    }

    private <T> List<T> crawlItems(final CrawlPageFunction<T> crawlPageFunction){
        int pageNo = 1;
        List<T> allItems = new ArrayList<>();

        while (true) {
            try {
                List<T> items = crawlPageFunction.crawlPage(pageNo);
                if (items.isEmpty()) {
                    break;
                }
                allItems.addAll(items);
                pageNo++;
            } catch (Exception e) {
                throw new CrawlException(500, "공공데이터 DURU 호출 중 예기치 못한 오류 발생 :" +  e.getMessage());
            }
        }
        return allItems;
    }

    private <T> List<T> getItems(AbstractDuruResponse.Body<T> body) {
        if (body == null || body.getItems() == null || body.getItems().getItem() == null) {
            return new ArrayList<>();
        }
        return body.getItems().getItem();
    }

    private List<DuruCourseResponse.Course> crawlCoursePage(final int pageNo) throws JsonProcessingException {
        DuruCourseResponse.Body<DuruCourseResponse.Course> body =
                crawlPage(pageNo, "courseList", DuruCourseResponse.class).getResponse().getBody();
        return getItems(body);
    }

    private List<DuruThemeResponse.Theme> crawlThemePage(final int pageNo) throws JsonProcessingException {
        DuruThemeResponse.Body<DuruThemeResponse.Theme> body =
                crawlPage(pageNo, "routeList", DuruThemeResponse.class).getResponse().getBody();
        return getItems(body);
    }


    private <T extends ValidatableResponse> T crawlPage(final int pageNo, final String endpoint, Class<T> responseType) throws JsonProcessingException {
        final URI uri = buildUri(endpoint, pageNo);
        final String response = apiResponseService.fetchApiResponse(uri);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        final T parsedResponse = objectMapper.readValue(response, responseType);
        crawlApiBuilderHelper.validateResponse(parsedResponse);
        return parsedResponse;
    }

    private URI buildUri(final String endpoint, final int pageNo) {
        return crawlApiBuilderHelper.buildUri(endpoint, duruApiProperties, pageNo, numOfRows);
    }

    @FunctionalInterface
    private interface CrawlPageFunction<T> {
        List<T> crawlPage(int pageNo) throws JsonProcessingException;
    }
}
