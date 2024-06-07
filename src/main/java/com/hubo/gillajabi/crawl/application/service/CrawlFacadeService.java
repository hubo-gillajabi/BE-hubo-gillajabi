package com.hubo.gillajabi.crawl.application.service;


import com.hubo.gillajabi.crawl.domain.entity.GpxInfo;
import com.hubo.gillajabi.crawl.domain.service.CrawlBusanServiceImpl;
import com.hubo.gillajabi.crawl.domain.service.CrawlDuruServiceImpl;
import com.hubo.gillajabi.crawl.domain.constant.CityName;
import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseDetail;
import com.hubo.gillajabi.crawl.domain.service.*;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruThemeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlFacadeService {

    private final CrawlDuruServiceImpl duruCrawlService;
    private final CrawlBusanServiceImpl busanCrawlService;
    private final CourseService courseService;
    private final CourseThemeSerivce courseThemeService;
    private final CityService cityService;
    private final GpxInfoService gpxInfoService;
    private final CourseDetailService courseDetailService;

    public String getCourse(final CityName cityName) {
        return switch (cityName) {
            case DURU -> handleDuruCourse();
            case BUSAN -> handleBusanCourse();
        };
    }

    private String handleDuruCourse() {
        List<DuruCourseResponse.Course> rawCourses = duruCrawlService.crawlCourse();
        List<City> cities = cityService.saveCity(rawCourses);
        List<Course> courses = courseService.saveDuruCourse(rawCourses, cities);
        List<CourseDetail> courseDetails = courseDetailService.saveDuruCourseDetail(rawCourses, courses);
        List<GpxInfo> gpxInfos = gpxInfoService.saveGpxInfo(courseDetails);

        return String.format("""
                        %d개의 데이터를 가져왔습니다.
                        %d개의 도시가 저장되었습니다.
                        %d개의 코스가 저장되었습니다.
                        %d개의 코스 상세정보가 저장되었습니다.
                        """,
                rawCourses.size(), cities.size(), courses.size(), courseDetails.size()) +
                +gpxInfos.size() + "개의 gpx 정보가 저장되었습니다.";
    }

    private String handleBusanCourse() {
        busanCrawlService.crawlCourse();
        return "Busan 작성중";
    }

    public String getTheme(final CityName cityName) {
        return switch (cityName) {
            case DURU -> handleDuruTheme();
            case BUSAN -> handleBusanTheme();
        };
    }

    private String handleDuruTheme() {
        List<DuruThemeResponse.Theme> responseItems = duruCrawlService.crawlTheme();
        courseThemeService.saveDuruTheme(responseItems);
        return responseItems.size() + "개의 데이터가 저장되었습니다.";
    }

    private String handleBusanTheme() {
        busanCrawlService.crawlTheme();
        return "Busan 구성중";
    }
}


