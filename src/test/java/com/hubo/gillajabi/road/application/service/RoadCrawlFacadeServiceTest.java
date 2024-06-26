package com.hubo.gillajabi.road.application.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.hubo.gillajabi.crawl.application.service.*;
import com.hubo.gillajabi.crawl.application.response.RoadCrawlResponse;
import com.hubo.gillajabi.crawl.domain.constant.CityCrawlName;
import com.hubo.gillajabi.crawl.domain.service.busan.RoadBusanThemeHandler;
import com.hubo.gillajabi.crawl.domain.service.busan.RoadCrawlBusanCourseHandler;
import com.hubo.gillajabi.crawl.domain.service.duru.RoadDuruCourseHandler;
import com.hubo.gillajabi.crawl.domain.service.duru.RoadDuruThemeHandler;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class RoadCrawlFacadeServiceTest {

    @Mock
    private RoadDuruCourseHandler roadDuruCourseHandler;

    @Mock
    private RoadCrawlBusanCourseHandler roadCrawlBusanCourseHandler;

    @Mock
    private RoadDuruThemeHandler roadDuruThemeHandler;

    @Mock
    private RoadBusanThemeHandler roadBusanThemeHandler;

    @InjectMocks
    private RoadCrawlFacadeService roadCrawlFacadeService;

    private final FixtureMonkey fixtureMonkey = FixtureMonkey.create();

    @Test
    @DisplayName("두루 코스를 제대로 호출")
    public void testGetDuruCourse() {
       // given
        RoadCrawlResponse.CourseResult duruCourseResult = fixtureMonkey.giveMeBuilder(RoadCrawlResponse.CourseResult.class)
                        .sample();

        when(roadDuruCourseHandler.handle()).thenReturn(duruCourseResult);

        // when
        assertEquals(duruCourseResult, roadCrawlFacadeService.getCourse(CityCrawlName.DURU));

        // then
        verify(roadDuruCourseHandler).handle();
        verifyNoMoreInteractions(roadDuruThemeHandler, roadCrawlBusanCourseHandler, roadBusanThemeHandler);
    }

    @Test
    @DisplayName("부산 코스를 제대로 호출")
    public void testGetBusanCourse() {
        // given
        RoadCrawlResponse.CourseResult busanCourseResult = fixtureMonkey.giveMeBuilder(RoadCrawlResponse.CourseResult.class)
                .sample();

        when(roadCrawlBusanCourseHandler.handle()).thenReturn(busanCourseResult);

        // when
        assertEquals(busanCourseResult, roadCrawlFacadeService.getCourse(CityCrawlName.BUSAN));

        // then
        verify(roadCrawlBusanCourseHandler).handle();
        verifyNoMoreInteractions(roadDuruCourseHandler, roadDuruThemeHandler, roadBusanThemeHandler);
    }

    @Test
    @DisplayName("두루 테마를 제대로 호출")
    public void testGetDuruTheme() {
        // given
        RoadCrawlResponse.ThemeResult duruThemeResult = fixtureMonkey.giveMeBuilder(RoadCrawlResponse.ThemeResult.class)
                .sample();

        when(roadDuruThemeHandler.handle()).thenReturn(duruThemeResult);

        // when
        assertEquals(duruThemeResult, roadCrawlFacadeService.getTheme(CityCrawlName.DURU));

        // then
        verify(roadDuruThemeHandler).handle();
        verifyNoMoreInteractions(roadDuruCourseHandler, roadCrawlBusanCourseHandler, roadBusanThemeHandler);
    }

    @Test
    @DisplayName("부산 테마를 제대로 호출")
    public void testGetBusanTheme() {
        // given
        RoadCrawlResponse.ThemeResult busanThemeResult = fixtureMonkey.giveMeBuilder(RoadCrawlResponse.ThemeResult.class)
                .sample();

        when(roadBusanThemeHandler.handle()).thenReturn(busanThemeResult);

        // when
        assertEquals(busanThemeResult, roadCrawlFacadeService.getTheme(CityCrawlName.BUSAN));

        // then
        verify(roadBusanThemeHandler).handle();
        verifyNoMoreInteractions(roadDuruCourseHandler, roadDuruThemeHandler, roadCrawlBusanCourseHandler);
    }
}
