package com.hubo.gillajabi.crawl.domain.service.duru;

import com.hubo.gillajabi.crawl.application.dto.response.RoadCrawlResponse;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseThemeRequest;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiThemeResponse;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoadThemeDuruHandlerTest {

    @Mock
    private RoadCrawlDuruServiceImpl duruCrawlService;

    @Mock
    private RoadCourseDuruThemeService roadCourseDuruThemeService;

    @InjectMocks
    private RoadThemeDuruHandler roadThemeDuruHandler;

    @Test
    @DisplayName("두루누비 테마를 제대로 호출")
    void testHandle() {
        // given
        FixtureMonkey fixtureMonkey = FixtureMonkey.create();

        // Mock 생성
        List<ApiThemeResponse.Theme> mockResponseItems = fixtureMonkey.giveMe(ApiThemeResponse.Theme.class, 5);

        // CourseTheme 생성
        List<CourseTheme> mockThemes = mockResponseItems.stream()
                .map(theme -> CourseTheme.createCourseTheme(
                        new CourseThemeRequest(theme.getThemeNm(), theme.getLinemsg(), theme.getThemedescs())))
                .collect(Collectors.toList());

        when(duruCrawlService.crawlTheme()).thenReturn(mockResponseItems);
        when(roadCourseDuruThemeService.saveDuruTheme(anyList())).thenReturn(mockThemes);

        // when
        RoadCrawlResponse.ThemeResult result = roadThemeDuruHandler.handle();

        // then
        assertEquals(mockThemes.size(), result.getThemeCount());
        verify(duruCrawlService).crawlTheme();
        verify(roadCourseDuruThemeService).saveDuruTheme(mockResponseItems);
    }
}
