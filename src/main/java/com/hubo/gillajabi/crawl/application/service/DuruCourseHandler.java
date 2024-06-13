package com.hubo.gillajabi.crawl.application.service;

import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseDetail;
import com.hubo.gillajabi.crawl.domain.service.CityService;
import com.hubo.gillajabi.crawl.domain.service.CourseDetailService;
import com.hubo.gillajabi.crawl.domain.service.CourseService;
import com.hubo.gillajabi.crawl.domain.service.CrawlDuruServiceImpl;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DuruCourseHandler {

    private final CrawlDuruServiceImpl duruCrawlService;
    private final CityService cityService;
    private final CourseService courseService;
    private final CourseDetailService courseDetailService;
//    private final GpxInfoService gpxInfoService;

    public String handle() {
        List<DuruCourseResponse.Course> rawCourses = duruCrawlService.crawlCourse();

        List<City> cities = cityService.saveCity(rawCourses);
        List<Course> courses = courseService.saveDuruCourse(rawCourses, cities);
        List<CourseDetail> courseDetails = courseDetailService.saveDuruCourseDetail(rawCourses, courses);
//        List<GpxInfo> gpxInfos = gpxInfoService.saveGpxInfo(courseDetails);

        return String.format("""
                        %d개의 데이터를 가져왔습니다.
                        %d개의 도시가 저장되었습니다.
                        %d개의 코스가 저장되었습니다.
                        %d개의 코스 상세정보가 저장되었습니다.
                        """,
                rawCourses.size(), cities.size(), courses.size(), courseDetails.size());
//                + gpxInfos.size() + "개의 gpx 정보가 저장되었습니다.";
    }
}
