package com.hubo.gillajabi.crawl.application.service;

import com.hubo.gillajabi.crawl.application.dto.response.CrawlResponse;
import com.hubo.gillajabi.crawl.domain.constant.CourseLevel;
import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseDetail;
import com.hubo.gillajabi.crawl.domain.entity.GpxInfo;
import com.hubo.gillajabi.crawl.domain.service.duru.*;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CityRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Test
    @DisplayName("두루누비 코스를 제대로 호출")
    void testHandle() {
        // given
        List<DuruCourseResponse.Course> mockRawCourses = IntStream.range(0, 5)
                .mapToObj(i -> {
                    DuruCourseResponse.Course course = new DuruCourseResponse.Course();
                    course.setCrsKorNm("Course " + i);
                    return course;
                }).collect(Collectors.toList());

        List<CityRequestDTO> cityRequestDTOs = mockRawCourses.stream()
                .map(course -> CityRequestDTO.of(course.getCrsKorNm(), Province.GYEONGGI, "짧은소개글"))
                .toList();

        List<City> mockCities = cityRequestDTOs.stream()
                .map(City::createCity)
                .collect(Collectors.toList());

        List<CourseDetail> mockCourseDetails = IntStream.range(0, 5)
                .mapToObj(i -> new CourseDetail(
                        null,
                        "투어정보" + i,
                        "짧은소개글" + i,
                        "시작점" + i,
                        "도착점" + i,
                        "시작점정보" + i,
                        "도착점정보" + i,
                        60,
                        "GPX Path" + i,
                        null
                )).collect(Collectors.toList());

        List<Course> mockCourses = IntStream.range(0, 5)
                .mapToObj(i -> Course.builder()
                        .originName(mockRawCourses.get(i).getCrsKorNm())
                        .distance(100)
                        .totalTimeRequired(60)
                        .level(CourseLevel.LOW)
                        .shortDescription("짧은소개글")
                        .courseNumber("1")
                        .city(mockCities.get(i))
                        .courseDetail(mockCourseDetails.get(i))
                        .build())
                .collect(Collectors.toList());

        List<GpxInfo> mockGpxInfos = IntStream.range(0, 5)
                .mapToObj(i -> GpxInfo.of("GPX Data" + i, mockCourseDetails.get(i)))
                .collect(Collectors.toList());

        when(duruCrawlService.crawlCourse()).thenReturn(mockRawCourses);
        when(cityService.saveCity(anyList())).thenReturn(mockCities);
        when(courseDuruService.saveDuruCourse(anyList(), anyList())).thenReturn(mockCourses);
        when(courseDetailDuruService.saveDuruCourseDetail(anyList(), anyList())).thenReturn(mockCourseDetails);
        when(gpxInfoDuruService.saveGpxInfo(anyList())).thenReturn(mockGpxInfos);

        // when
        CrawlResponse.CourseResult result = duruCourseHandler.handle();

        // then
        assertEquals(5, result.getCityCount());
        assertEquals(5, result.getCourseCount());
        assertEquals(5, result.getCourseDetailCount());
        assertEquals(5, result.getGpxInfoCount());

        verify(duruCrawlService).crawlCourse();
        verify(cityService).saveCity(anyList());
        verify(courseDuruService).saveDuruCourse(anyList(), anyList());
        verify(courseDetailDuruService).saveDuruCourseDetail(anyList(), anyList());
        verify(gpxInfoDuruService).saveGpxInfo(anyList());
    }
}
