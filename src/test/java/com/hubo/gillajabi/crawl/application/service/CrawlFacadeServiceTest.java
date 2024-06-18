package com.hubo.gillajabi.crawl.application.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.hubo.gillajabi.crawl.application.dto.response.CrawlResponse;
import com.hubo.gillajabi.crawl.domain.constant.CityName;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CrawlFacadeServiceTest {

    @Mock
    private DuruCourseHandler duruCourseHandler;

    @Mock
    private BusanCourseHandler busanCourseHandler;

    @Mock
    private DuruThemeHandler duruThemeHandler;

    @Mock
    private BusanThemeHandler busanThemeHandler;

    @InjectMocks
    private CrawlFacadeService crawlFacadeService;

    private final FixtureMonkey fixtureMonkey = FixtureMonkey.create();

    @Test
    @DisplayName("두루 코스를 제대로 호출")
    public void testGetDuruCourse() {
       // given
        CrawlResponse.CourseResult duruCourseResult = fixtureMonkey.giveMeBuilder(CrawlResponse.CourseResult.class)
                        .sample();

        when(duruCourseHandler.handle()).thenReturn(duruCourseResult);

        // when
        assertEquals(duruCourseResult, crawlFacadeService.getCourse(CityName.DURU));

        // then
        verify(duruCourseHandler).handle();
        verifyNoMoreInteractions(duruThemeHandler, busanCourseHandler, busanThemeHandler);
    }

    @Test
    @DisplayName("부산 코스를 제대로 호출")
    public void testGetBusanCourse() {
        // given
        CrawlResponse.CourseResult busanCourseResult = fixtureMonkey.giveMeBuilder(CrawlResponse.CourseResult.class)
                .sample();

        when(busanCourseHandler.handle()).thenReturn(busanCourseResult);

        // when
        assertEquals(busanCourseResult, crawlFacadeService.getCourse(CityName.BUSAN));

        // then
        verify(busanCourseHandler).handle();
        verifyNoMoreInteractions(duruCourseHandler, duruThemeHandler, busanThemeHandler);
    }

    @Test
    @DisplayName("두루 테마를 제대로 호출")
    public void testGetDuruTheme() {
        // given
        CrawlResponse.ThemeResult duruThemeResult = fixtureMonkey.giveMeBuilder(CrawlResponse.ThemeResult.class)
                .sample();

        when(duruThemeHandler.handle()).thenReturn(duruThemeResult);

        // when
        assertEquals(duruThemeResult, crawlFacadeService.getTheme(CityName.DURU));

        // then
        verify(duruThemeHandler).handle();
        verifyNoMoreInteractions(duruCourseHandler, busanCourseHandler, busanThemeHandler);
    }

    @Test
    @DisplayName("부산 테마를 제대로 호출")
    public void testGetBusanTheme() {
        // given
        CrawlResponse.ThemeResult busanThemeResult = fixtureMonkey.giveMeBuilder(CrawlResponse.ThemeResult.class)
                .sample();

        when(busanThemeHandler.handle()).thenReturn(busanThemeResult);

        // when
        assertEquals(busanThemeResult, crawlFacadeService.getTheme(CityName.BUSAN));

        // then
        verify(busanThemeHandler).handle();
        verifyNoMoreInteractions(duruCourseHandler, duruThemeHandler, busanCourseHandler);
    }
}
