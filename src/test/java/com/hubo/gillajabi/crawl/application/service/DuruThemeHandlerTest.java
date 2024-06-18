package com.hubo.gillajabi.crawl.application.service;

import com.hubo.gillajabi.crawl.application.dto.response.CrawlResponse;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.domain.service.duru.CourseDuruThemeService;
import com.hubo.gillajabi.crawl.domain.service.duru.CrawlDuruServiceImpl;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseThemeRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruThemeResponse;
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
class DuruThemeHandlerTest {

    @Mock
    private CrawlDuruServiceImpl duruCrawlService;

    @Mock
    private CourseDuruThemeService courseDuruThemeService;

    @InjectMocks
    private DuruThemeHandler duruThemeHandler;

    @Test
    @DisplayName("두루누비 테마를 제대로 호출")
    void testHandle() {
        // given
        FixtureMonkey fixtureMonkey = FixtureMonkey.create();

        // Mock 생성
        List<DuruThemeResponse.Theme> mockResponseItems = fixtureMonkey.giveMe(DuruThemeResponse.Theme.class, 5);

        // CourseTheme 생성
        List<CourseTheme> mockThemes = mockResponseItems.stream()
                .map(theme -> CourseTheme.createCourseTheme(
                        new CourseThemeRequestDTO(theme.getThemeNm(), theme.getLinemsg(), theme.getThemedescs())))
                .collect(Collectors.toList());

        when(duruCrawlService.crawlTheme()).thenReturn(mockResponseItems);
        when(courseDuruThemeService.saveDuruTheme(anyList())).thenReturn(mockThemes);

        // when
        CrawlResponse.ThemeResult result = duruThemeHandler.handle();

        // then
        assertEquals(mockThemes.size(), result.getThemeCount());
        verify(duruCrawlService).crawlTheme();
        verify(courseDuruThemeService).saveDuruTheme(mockResponseItems);
    }
}
