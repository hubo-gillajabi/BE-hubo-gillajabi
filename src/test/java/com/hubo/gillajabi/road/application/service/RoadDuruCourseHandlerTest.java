package com.hubo.gillajabi.road.application.service;

import com.hubo.gillajabi.crawl.domain.service.duru.RoadDuruCourseHandler;
import com.hubo.gillajabi.crawl.application.response.RoadCrawlResponse;
import com.hubo.gillajabi.crawl.domain.constant.CourseLevel;
import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.domain.entity.*;
import com.hubo.gillajabi.crawl.domain.service.duru.*;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CityRequest;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseDetailRequest;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseRequest;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseThemeRequest;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;
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
class RoadDuruCourseHandlerTest {

    @Mock
    private RoadCrawlDuruServiceImpl duruCrawlService;

    @Mock
    private RoadCityDuruService cityService;

    @Mock
    private RoadCourseDuruService roadCourseDuruService;

    @Mock
    private RoadCourseDetailDuruService roadCourseDetailDuruService;

    @Mock
    private RoadGpxInfoDuruService roadGpxInfoDuruService;

    @InjectMocks
    private RoadDuruCourseHandler roadDuruCourseHandler;

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder().build();

    public ApiCourseResponse.Course createDuruCourseResponse(){
        ApiCourseResponse.Course response = fixtureMonkey.giveMeBuilder(ApiCourseResponse.Course.class).sample();
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
        CourseDetailRequest courseDetailRequest = fixtureMonkey.giveMeOne(CourseDetailRequest.class);
        return CourseDetail.createCourseDetail(courseDetailRequest);
    }

    private static Course createCourse(ApiCourseResponse.Course mockCourseDuruResponse, City mockCity, CourseTheme mockCourseTheme) {
        CourseRequest courseRequest = CourseRequest.of(mockCourseDuruResponse, mockCity, mockCourseTheme);
        courseRequest.setCity(mockCity);
        courseRequest.setLevel(CourseLevel.fromValue(mockCourseDuruResponse.getCrsLevel()));

        return Course.createCourse(courseRequest);
    }

    private static CourseTheme createCourseTheme() {
        CourseThemeRequest courseThemeRequest = fixtureMonkey.giveMeOne(CourseThemeRequest.class);
        return CourseTheme.createCourseTheme(courseThemeRequest);
    }

    private static City createCity(ApiCourseResponse.Course mockCourseDuruResponse) {
        CityRequest cityRequest = CityRequest.of(mockCourseDuruResponse.getCrsKorNm(), Province.GYEONGGI, "짧은소개글");
        return City.createCity(cityRequest);
    }

    private void mockDuruHandle() {
        ApiCourseResponse.Course mockCourseDuruResponse = createDuruCourseResponse();
        City mockCity = createCity(mockCourseDuruResponse);
        CourseTheme mockCourseTheme = createCourseTheme();
        Course mockCourse = createCourse(mockCourseDuruResponse, mockCity, mockCourseTheme);
        CourseDetail mockCourseDetail = createCourseDetail();
        GpxInfo mockGpxInfo = GpxInfo.of("http://gpxpath.com", mockCourseDetail);

        when(duruCrawlService.crawlCourse()).thenReturn(List.of(mockCourseDuruResponse));
        when(cityService.saveCity(anyList())).thenReturn(List.of(mockCity));
        when(roadCourseDuruService.saveDuruCourse(anyList(), anyList())).thenReturn(List.of(mockCourse));
        when(roadCourseDetailDuruService.saveDuruCourseDetail(anyList(), anyList())).thenReturn(List.of(mockCourseDetail));
        when(roadGpxInfoDuruService.saveGpxInfo(anyList())).thenReturn(List.of(mockGpxInfo));
    }


    @Test
    @DisplayName("두루누비 코스를 제대로 호출")
    void testHandle() {
        // given
        mockDuruHandle();

        // when
        RoadCrawlResponse.CourseResult result = roadDuruCourseHandler.handle();

        // then
        assertEquals(1, result.getCityCount());
        assertEquals(1, result.getCourseCount());
        assertEquals(1, result.getCourseDetailCount());
        assertEquals(1, result.getGpxInfoCount());

    }


}
