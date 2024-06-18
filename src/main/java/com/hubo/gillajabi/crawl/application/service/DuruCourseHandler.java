package com.hubo.gillajabi.crawl.application.service;

import com.hubo.gillajabi.crawl.application.dto.response.CrawlResponse;
import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseDetail;
import com.hubo.gillajabi.crawl.domain.entity.GpxInfo;
import com.hubo.gillajabi.crawl.domain.service.duru.*;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DuruCourseHandler {

    private final CrawlDuruServiceImpl duruCrawlService;
    private final CityDuruService cityService;
    private final CourseDuruService courseDuruService;
    private final CourseDetailDuruService courseDetailDuruService;
    private final GpxInfoDuruService gpxInfoDuruService;

    public CrawlResponse.CourseResult handle() {
        List<DuruCourseResponse.Course> rawCourses = duruCrawlService.crawlCourse();
        List<City> cities = cityService.saveCity(rawCourses);
        List<Course> courses = courseDuruService.saveDuruCourse(rawCourses, cities);
        List<CourseDetail> courseDetails = courseDetailDuruService.saveDuruCourseDetail(rawCourses, courses);
        List<GpxInfo> gpxInfos = gpxInfoDuruService.saveGpxInfo(courseDetails);

        return CrawlResponse.CourseResult.of(cities, courses, courseDetails, gpxInfos);

    }
}
