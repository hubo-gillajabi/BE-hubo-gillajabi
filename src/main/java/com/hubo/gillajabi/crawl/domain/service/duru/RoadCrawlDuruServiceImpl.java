package com.hubo.gillajabi.crawl.domain.service.duru;

import com.hubo.gillajabi.crawl.domain.constant.CityCrawlName;
import com.hubo.gillajabi.crawl.domain.service.PrimaryCrawlingService;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.AbstractApiResponse;
import com.hubo.gillajabi.crawl.infrastructure.util.helper.CrawlApiBuilderHelper;
import com.hubo.gillajabi.crawl.infrastructure.config.RoadEndpointConfig;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiThemeResponse;
import com.hubo.gillajabi.global.common.dto.ApiProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoadCrawlDuruServiceImpl extends RoadDuruAbstractCrawlDuruService {

    private final RoadEndpointConfig roadEndpointConfig;
    private final CrawlApiBuilderHelper crawlApiBuilderHelper;
    private final PrimaryCrawlingService primaryCrawlingService;
    private static ApiProperties duruApiProperties;

    @PostConstruct
    protected void init() {
        duruApiProperties = roadEndpointConfig.getEndpoint(CityCrawlName.DURU);
        validateDuruApiProperties();
    }

    private void validateDuruApiProperties() {
        if (duruApiProperties == null) {
            throw new IllegalStateException("DURU Crawl Service가 초기화 되지 않았습니다.");
        }
    }

    @Override
    public List<ApiCourseResponse.Course> crawlCourse() {
        return primaryCrawlingService.crawlItems(this::crawlCoursePage);
    }

    @Override
    public List<ApiThemeResponse.Theme> crawlTheme() {
        return primaryCrawlingService.crawlItems(this::crawlThemePage);
    }

    private AbstractApiResponse<ApiCourseResponse.Course> crawlCoursePage(int pageNo) throws Exception {
        URI uri = crawlApiBuilderHelper.buildUri("courseList", duruApiProperties, pageNo);
        return primaryCrawlingService.crawlPage(ApiCourseResponse.class, uri);
    }

    private AbstractApiResponse<ApiThemeResponse.Theme> crawlThemePage(int pageNo) throws Exception {
        URI uri = crawlApiBuilderHelper.buildUri("routeList", duruApiProperties, pageNo);
        return primaryCrawlingService.crawlPage(ApiThemeResponse.class, uri);
    }


}
