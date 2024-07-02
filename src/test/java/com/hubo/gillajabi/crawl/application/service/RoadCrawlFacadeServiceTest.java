package com.hubo.gillajabi.crawl.application.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.hubo.gillajabi.crawl.application.dto.response.RoadCrawlResponse;
import com.hubo.gillajabi.crawl.domain.constant.CityCrawlName;
import com.hubo.gillajabi.crawl.domain.service.busan.RoadBusanThemeHandler;
import com.hubo.gillajabi.crawl.domain.service.busan.RoadCrawlBusanCourseHandler;
import com.hubo.gillajabi.crawl.domain.service.duru.RoadCourseDuruHandler;
import com.hubo.gillajabi.crawl.domain.service.duru.RoadThemeDuruHandler;
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
    private RoadCourseDuruHandler roadCourseDuruHandler;

    @Mock
    private RoadCrawlBusanCourseHandler roadCrawlBusanCourseHandler;

    @Mock
    private RoadThemeDuruHandler roadThemeDuruHandler;

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

        when(roadCourseDuruHandler.handle()).thenReturn(duruCourseResult);

        // when
        assertEquals(duruCourseResult, roadCrawlFacadeService.getCourse(CityCrawlName.DURU));

        // then
        verify(roadCourseDuruHandler).handle();
        verifyNoMoreInteractions(roadThemeDuruHandler, roadCrawlBusanCourseHandler, roadBusanThemeHandler);
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
        verifyNoMoreInteractions(roadCourseDuruHandler, roadThemeDuruHandler, roadBusanThemeHandler);
    }

    @Test
    @DisplayName("두루 테마를 제대로 호출")
    public void testGetDuruTheme() {
        // given
        RoadCrawlResponse.ThemeResult duruThemeResult = fixtureMonkey.giveMeBuilder(RoadCrawlResponse.ThemeResult.class)
                .sample();

        when(roadThemeDuruHandler.handle()).thenReturn(duruThemeResult);

        // when
        assertEquals(duruThemeResult, roadCrawlFacadeService.getTheme(CityCrawlName.DURU));

        // then
        verify(roadThemeDuruHandler).handle();
        verifyNoMoreInteractions(roadCourseDuruHandler, roadCrawlBusanCourseHandler, roadBusanThemeHandler);
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
        verifyNoMoreInteractions(roadCourseDuruHandler, roadThemeDuruHandler, roadCrawlBusanCourseHandler);
    }
}
