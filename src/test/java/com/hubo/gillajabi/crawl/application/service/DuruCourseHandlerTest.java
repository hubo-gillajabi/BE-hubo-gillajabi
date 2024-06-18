package com.hubo.gillajabi.crawl.application.service;

import com.hubo.gillajabi.crawl.application.dto.response.CrawlResponse;
import com.hubo.gillajabi.crawl.domain.constant.CourseLevel;
import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.domain.entity.*;
import com.hubo.gillajabi.crawl.domain.service.duru.*;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CityRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseDetailRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseThemeRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DuruCourseHandlerTest {

    @Mock
    private CrawlDuruServiceImpl duruCrawlService;

    @Mock
    private CityDuruService cityService;

    @Mock
    private CourseDuruService courseDuruService;

    @Mock
    private CourseDetailDuruService courseDetailDuruService;

    @Mock
    private GpxInfoDuruService gpxInfoDuruService;

    @InjectMocks
    private DuruCourseHandler duruCourseHandler;

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder().build();

    @Test
    @DisplayName("두루누비 코스를 제대로 호출")
    void testHandle() {
        // given
        DuruCourseResponse.Course mockCourseDuruResponse = fixtureMonkey.giveMeOne(DuruCourseResponse.Course.class);
        mockCourseDuruResponse.setCrsLevel("1");
        mockCourseDuruResponse.setCrsCycle("순환형");
        mockCourseDuruResponse.setCrsKorNm("두루누비 코스");
        mockCourseDuruResponse.setCrsDstnc("10");
        mockCourseDuruResponse.setCrsSummary("요약");
        mockCourseDuruResponse.setCrsTotlRqrmHour("1");

        CityRequestDTO cityRequestDTO = CityRequestDTO.of(mockCourseDuruResponse.getCrsKorNm(), Province.GYEONGGI, "짧은소개글");
        City mockCity = City.createCity(cityRequestDTO);

        CourseThemeRequestDTO courseThemeRequestDTO = fixtureMonkey.giveMeOne(CourseThemeRequestDTO.class);
        CourseTheme mockCourseTheme = CourseTheme.createCourseTheme(courseThemeRequestDTO);

        CourseRequestDTO courseRequestDTO = CourseRequestDTO.of(mockCourseDuruResponse, mockCity, mockCourseTheme);
        courseRequestDTO.setCity(mockCity);
        courseRequestDTO.setLevel(CourseLevel.fromValue(mockCourseDuruResponse.getCrsLevel()));

        Course mockCourse = Course.createCourse(courseRequestDTO);

        CourseDetailRequestDTO courseDetailRequestDTO = fixtureMonkey.giveMeOne(CourseDetailRequestDTO.class);
        CourseDetail mockCourseDetail = CourseDetail.createCourseDetail(courseDetailRequestDTO);

        GpxInfo mockGpxInfo = fixtureMonkey.giveMeOne(GpxInfo.class);

        when(duruCrawlService.crawlCourse()).thenReturn(List.of(mockCourseDuruResponse));
        when(cityService.saveCity(anyList())).thenReturn(List.of(mockCity));
        when(courseDuruService.saveDuruCourse(anyList(), anyList())).thenReturn(List.of(mockCourse));
        when(courseDetailDuruService.saveDuruCourseDetail(anyList(), anyList())).thenReturn(List.of(mockCourseDetail));
        when(gpxInfoDuruService.saveGpxInfo(anyList())).thenReturn(List.of(mockGpxInfo));

        // when
        CrawlResponse.CourseResult result = duruCourseHandler.handle();

        // then
        assertEquals(1, result.getCityCount());
        assertEquals(1, result.getCourseCount());
        assertEquals(1, result.getCourseDetailCount());
        assertEquals(1, result.getGpxInfoCount());

    }
}
