package com.hubo.gillajabi.crawl.domain.service.duru;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseRequest;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseThemeRepository;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import com.hubo.gillajabi.crawl.infrastructure.util.helper.RoadCrawlResponseParserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoadCourseDuruService {

    private final CourseRepository courseRepository;
    private final CourseThemeRepository courseThemeRepository;

    public List<Course> saveDuruCourse(final List<ApiCourseResponse.Course> responseItems, final List<City> cities) {
        List<Course> courses = new ArrayList<>();
        responseItems.forEach(item -> courses.add(processCourseItem(item, cities)));
        return courses;
    }

    private Course processCourseItem(ApiCourseResponse.Course item, List<City> cities) {
        City city = findCity(item, cities);
        CourseTheme courseTheme = findCourseTheme(item);
        CourseRequest courseRequest = CourseRequest.of(item, city, courseTheme);

        Optional<Course> existingCourse = courseRepository.findByOriginName(item.getCrsKorNm());
        return createOrUpdateCourse(courseRequest, existingCourse);
    }

    private City findCity(final ApiCourseResponse.Course item, final List<City> cities) {
        final String provinceName = RoadCrawlResponseParserHelper.parseDuruResponseByProvince(item.getSigun());
        final Province province = Province.fromValue(provinceName);

        final String cityName = province.isBigCity()
                ? provinceName
                : RoadCrawlResponseParserHelper.parseDuruResponseByCity(item.getSigun());

        return cities.stream()
                .filter(c -> c.getName().equals(cityName) && c.getProvince().equals(province))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("City 정보가 없습니다."));
    }

    private CourseTheme findCourseTheme(final ApiCourseResponse.Course item) {
        final String courseName = item.getCrsKorNm().split(" ")[0];
        return courseThemeRepository.findByName(courseName)
                .orElseThrow(() -> new IllegalArgumentException("CourseTheme 정보가 없습니다."));
    }

    private Course createOrUpdateCourse(final CourseRequest request, final Optional<Course> existingCourse) {
        return existingCourse
                .map(course -> updateExistingCourse(request, course))
                .orElseGet(() -> createAndSaveNewCourse(request));
    }

    private Course updateExistingCourse(final CourseRequest request, final Course existingCourse) {
        if (existingCourse.checkUpdate(request)) {
            existingCourse.update(request);
            courseRepository.save(existingCourse);
        }
        return existingCourse;
    }

    private Course createAndSaveNewCourse(final CourseRequest request) {
        Course newCourse = Course.createCourse(request);
        courseRepository.save(newCourse);
        return newCourse;
    }
}
