package com.hubo.gillajabi.crawl.domain.service.duru;

import com.hubo.gillajabi.crawl.domain.service.PrimaryCrawlingService;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiThemeResponse;
import com.hubo.gillajabi.crawl.infrastructure.util.helper.CrawlApiBuilderHelper;
import com.hubo.gillajabi.crawl.infrastructure.config.RoadEndpointConfig;
import com.hubo.gillajabi.global.common.dto.ApiProperties;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoadCrawlDuruServiceTest {

    @Mock
    private RoadEndpointConfig roadEndpointConfig;

    @Mock
    private CrawlApiBuilderHelper crawlApiBuilderHelper;

    @Mock
    private PrimaryCrawlingService primaryCrawlingService;

    @InjectMocks
    private RoadCrawlDuruServiceImpl crawlDuruService;

    @BeforeEach
    void setUp() {
        ApiProperties duruApiProperties = new ApiProperties();
        duruApiProperties.setSiteUrl("http://example.com/api");
        when(roadEndpointConfig.getEndpoint(any())).thenReturn(duruApiProperties);
        crawlDuruService.init();
    }

//    @Test
//    @DisplayName("두루누비 코스 크롤링 테스트")
//    void testCrawlCourse() throws URISyntaxException {
//        // given
//        makeCrawlCourseMock();
//
//        // when
//        List<ApiCourseResponse.Course> courses = crawlDuruService.crawlCourse();
//
//        // then
//        assertEquals(1, courses.size());
//        assertEquals("서해랑길 53코스", courses.get(0).getCrsKorNm());
//    }
//
//    private void makeCrawlCourseMock() throws URISyntaxException {
//        URI uriCoursePage1 = new URI("http://example.com/api/courseList?page=1");
//
//        when(crawlApiBuilderHelper.buildUri(eq("courseList"), any(), eq(1))).thenReturn(uriCoursePage1);
//
//        ApiCourseResponse courseResponsePage1 = new ApiCourseResponse();
//
//        when(primaryCrawlingService.crawlPage(eq(ApiCourseResponse.class), eq(uriCoursePage1))).thenReturn(courseResponsePage1);
//    }
//
//    @Test
//    @DisplayName("두루누비 테마 크롤링 테스트")
//    void testCrawlTheme() throws URISyntaxException {
//        // given
//        makeCrawlThemeMock();
//
//        // when
//        List<ApiThemeResponse.Theme> themes = crawlDuruService.crawlTheme();
//
//        // then
//        assertFalse(themes.isEmpty());
//        assertEquals(1, themes.size());
//    }
//
//    private void makeCrawlThemeMock() throws URISyntaxException {
//        URI uriThemePage1 = new URI("http://example.com/api/routeList?page=1");
//
//        when(crawlApiBuilderHelper.buildUri(eq("routeList"), any(), eq(1))).thenReturn(uriThemePage1);
//
//        ApiThemeResponse themeResponsePage1 = FixtureMonkey.builder().build().giveMeOne(ApiThemeResponse.class);
//
//        when(primaryCrawlingService.crawlPage(eq(ApiThemeResponse.class), eq(uriThemePage1))).thenReturn(themeResponsePage1);
//    }
}
