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

    public DuruCourseResponse.Course createDuruCourseResponse(){
        DuruCourseResponse.Course response = fixtureMonkey.giveMeBuilder(DuruCourseResponse.Course.class).sample();
        response.setCrsKorNm("남해랑길 1코스");
        response.setCrsLevel("1");
        response.setGpxpath("http://example.com");
        response.setCrsCycle("비순환형");
        response.setCrsSummary("남해랑길 1코스 소개");
        response.setCrsTotlRqrmHour("1");
        response.setCrsDstnc("1");
        return response;
    }
    private static CourseDetail createCourseDetail() {
        CourseDetailRequestDTO courseDetailRequestDTO = fixtureMonkey.giveMeOne(CourseDetailRequestDTO.class);
        return CourseDetail.createCourseDetail(courseDetailRequestDTO);
    }

    private static Course createCourse(DuruCourseResponse.Course mockCourseDuruResponse, City mockCity, CourseTheme mockCourseTheme) {
        CourseRequestDTO courseRequestDTO = CourseRequestDTO.of(mockCourseDuruResponse, mockCity, mockCourseTheme);
        courseRequestDTO.setCity(mockCity);
        courseRequestDTO.setLevel(CourseLevel.fromValue(mockCourseDuruResponse.getCrsLevel()));

        return Course.createCourse(courseRequestDTO);
    }

    private static CourseTheme createCourseTheme() {
        CourseThemeRequestDTO courseThemeRequestDTO = fixtureMonkey.giveMeOne(CourseThemeRequestDTO.class);
        return CourseTheme.createCourseTheme(courseThemeRequestDTO);
    }

    private static City createCity(DuruCourseResponse.Course mockCourseDuruResponse) {
        CityRequestDTO cityRequestDTO = CityRequestDTO.of(mockCourseDuruResponse.getCrsKorNm(), Province.GYEONGGI, "짧은소개글");
        return City.createCity(cityRequestDTO);
    }

    private void mockDuruHandle() {
        DuruCourseResponse.Course mockCourseDuruResponse = createDuruCourseResponse();
        City mockCity = createCity(mockCourseDuruResponse);
        CourseTheme mockCourseTheme = createCourseTheme();
        Course mockCourse = createCourse(mockCourseDuruResponse, mockCity, mockCourseTheme);
        CourseDetail mockCourseDetail = createCourseDetail();
        GpxInfo mockGpxInfo = GpxInfo.of("http://gpxpath.com", mockCourseDetail);

        when(duruCrawlService.crawlCourse()).thenReturn(List.of(mockCourseDuruResponse));
        when(cityService.saveCity(anyList())).thenReturn(List.of(mockCity));
        when(courseDuruService.saveDuruCourse(anyList(), anyList())).thenReturn(List.of(mockCourse));
        when(courseDetailDuruService.saveDuruCourseDetail(anyList(), anyList())).thenReturn(List.of(mockCourseDetail));
        when(gpxInfoDuruService.saveGpxInfo(anyList())).thenReturn(List.of(mockGpxInfo));
    }


    @Test
    @DisplayName("두루누비 코스를 제대로 호출")
    void testHandle() {
        // given
        mockDuruHandle();

        // when
        CrawlResponse.CourseResult result = duruCourseHandler.handle();

        // then
        assertEquals(1, result.getCityCount());
        assertEquals(1, result.getCourseCount());
        assertEquals(1, result.getCourseDetailCount());
        assertEquals(1, result.getGpxInfoCount());

    }


}
