package com.hubo.gillajabi.crawl.domain.service.duru;

import com.hubo.gillajabi.crawl.domain.service.ApiResponseService;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruThemeResponse;
import com.hubo.gillajabi.crawl.infrastructure.util.helper.CrawlApiBuilderHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrawlDuruServiceTest {

    @Mock
    private ApiResponseService apiResponseService;

    @Mock
    private CrawlApiBuilderHelper crawlApiBuilderHelper;

    @InjectMocks
    private CrawlDuruServiceImpl crawlDuruService;

    private String readJsonFile(String path) throws IOException {
        Path basePath = Paths.get("src", "test", "resources");
        Path fullPath = basePath.resolve(path);
        return Files.readString(fullPath, StandardCharsets.UTF_8);
    }

    @Test
    @DisplayName("두루누비 코스 크롤링 테스트")
    void testCrawlCourse() throws URISyntaxException, IOException {
        // given
        makeCrawlCourseMock();

        // when
        List<DuruCourseResponse.Course> courses = crawlDuruService.crawlCourse();

        // then
        assertEquals(1, courses.size());
        assertEquals("서해랑길 53코스", courses.get(0).getCrsKorNm());
    }

    private void makeCrawlCourseMock() throws URISyntaxException, IOException {
        URI uriCoursePage1 = new URI("http://example.com/api/courses?page=1");
        URI uriCoursePage2 = new URI("http://example.com/api/courses?page=2");

        when(crawlApiBuilderHelper.buildUri(eq("courseList"), any(), eq(1), eq(100))).thenReturn(uriCoursePage1);
        when(crawlApiBuilderHelper.buildUri(eq("courseList"), any(), eq(2), eq(100))).thenReturn(uriCoursePage2);

        String courseResponsePage1 = readJsonFile("mockedResponses/courseDuruResponsePage1.json");
        String courseResponsePage2 = readJsonFile("mockedResponses/courseDuruResponsePage2.json");
        when(apiResponseService.fetchApiResponse(uriCoursePage1)).thenReturn(courseResponsePage1);
        when(apiResponseService.fetchApiResponse(uriCoursePage2)).thenReturn(courseResponsePage2);
    }

    @Test
    @DisplayName("두루누비 테마 크롤링 테스트")
    void testCrawlTheme() throws URISyntaxException, IOException {
        // given
        makeCrawlThemeMock();

        // when
        List<DuruThemeResponse.Theme> themes = crawlDuruService.crawlTheme();

        // then
        assertFalse(themes.isEmpty());
        assertEquals(3, themes.size());
    }

    private void makeCrawlThemeMock() throws URISyntaxException, IOException {
        URI uriThemePage1 = new URI("http://example.com/api/themes?page=1");
        URI uriThemePage2 = new URI("http://example.com/api/themes?page=2");

        when(crawlApiBuilderHelper.buildUri(eq("routeList"), any(), eq(1), eq(100))).thenReturn(uriThemePage1);
        when(crawlApiBuilderHelper.buildUri(eq("routeList"), any(), eq(2), eq(100))).thenReturn(uriThemePage2);

        String themeResponsePage1 = readJsonFile("mockedResponses/themeDuruResponsePage1.json");
        String themeResponsePage2 = readJsonFile("mockedResponses/themeDuruResponsePage2.json");
        when(apiResponseService.fetchApiResponse(uriThemePage1)).thenReturn(themeResponsePage1);
        when(apiResponseService.fetchApiResponse(uriThemePage2)).thenReturn(themeResponsePage2);
    }
}
