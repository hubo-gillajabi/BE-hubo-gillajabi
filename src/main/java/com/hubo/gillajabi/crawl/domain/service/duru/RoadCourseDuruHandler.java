package com.hubo.gillajabi.crawl.domain.service.duru;

import com.hubo.gillajabi.crawl.application.dto.response.RoadCrawlResponse;
import com.hubo.gillajabi.city.domain.City;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseDetail;
import com.hubo.gillajabi.crawl.domain.entity.GpxInfo;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoadCourseDuruHandler {

    private final RoadCrawlDuruServiceImpl duruCrawlService;
    private final RoadCityDuruService cityService;
    private final RoadCourseDuruService roadCourseDuruService;
    private final RoadCourseDetailDuruService roadCourseDetailDuruService;
    private final RoadGpxInfoDuruService roadGpxInfoDuruService;

    public RoadCrawlResponse.CourseResult handle() {
        List<ApiCourseResponse.Course> rawCourses = duruCrawlService.crawlCourse();
        List<City> cities = cityService.saveCity(rawCourses);
        List<Course> courses = roadCourseDuruService.saveDuruCourse(rawCourses, cities);
        List<CourseDetail> courseDetails = roadCourseDetailDuruService.saveDuruCourseDetail(rawCourses, courses);
        List<GpxInfo> gpxInfos = roadGpxInfoDuruService.saveGpxInfo(courseDetails);

        return RoadCrawlResponse.CourseResult.of(cities, courses, courseDetails, gpxInfos);

    }
}
