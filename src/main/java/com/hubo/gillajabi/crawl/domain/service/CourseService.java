package com.hubo.gillajabi.crawl.domain.service;

import com.hubo.gillajabi.crawl.domain.constant.CourseLevel;
import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseThemeRepository;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import com.hubo.gillajabi.crawl.infrastructure.util.helper.CrawlResponseParserHelper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseThemeRepository courseThemeRepository;

    public List<Course> saveDuruCourse(final List<DuruCourseResponse.Course> responseItems, final List<City> cities) {
        List<Course> courses = new ArrayList<>();
        responseItems.forEach(item -> courses.add(processCourseItem(item, cities)));
        return courses;
    }

    private Course processCourseItem(DuruCourseResponse.Course item, List<City> cities) {
        City city = findCity(item, cities);
        CourseTheme courseTheme = findCourseTheme(item);
        CourseRequestDTO courseRequestDTO = buildCourseRequest(item, city, courseTheme);

        Optional<Course> existingCourse = courseRepository.findByOriginName(item.getCrsKorNm());
        return createOrUpdateCourse(courseRequestDTO, existingCourse);
    }

    private City findCity(final DuruCourseResponse.Course item, final List<City> cities) {
        final String provinceName = CrawlResponseParserHelper.parseDuruResponseByProvince(item.getSigun());
        final Province province = Province.fromValue(provinceName);
        final String cityName = CrawlResponseParserHelper.parseDuruResponseByCity(item.getSigun());

        return cities.stream()
                .filter(c -> c.getName().equals(cityName) && c.getProvince().equals(province))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("City 정보가 없습니다."));
    }

    private CourseTheme findCourseTheme(final DuruCourseResponse.Course item) {
        final String courseName = item.getCrsKorNm().split(" ")[0];
        return courseThemeRepository.findByName(courseName)
                .orElseThrow(() -> new IllegalArgumentException("CourseTheme 정보가 없습니다."));
    }

    private CourseRequestDTO buildCourseRequest(DuruCourseResponse.Course item, City city, CourseTheme courseTheme) {
        CourseLevel level = CourseLevel.fromValue(item.getCrsLevel());
        String shortDescription = Jsoup.parse(item.getCrsSummary()).text();
        String courseNumber = parseCourseNumber(item.getCrsKorNm());

        return CourseRequestDTO.of(
                item.getCrsKorNm(),
                Integer.parseInt(item.getCrsDstnc()),
                Integer.parseInt(item.getCrsTotlRqrmHour()),
                level,
                shortDescription,
                courseNumber,
                city,
                courseTheme
        );
    }

    private Course createOrUpdateCourse(final CourseRequestDTO request, final Optional<Course> existingCourse) {
        return existingCourse
                .map(course -> updateExistingCourse(request, course))
                .orElseGet(() -> createAndSaveNewCourse(request));
    }

    private Course updateExistingCourse(final CourseRequestDTO request, final Course existingCourse) {
        if (existingCourse.checkUpdate(request)) {
            existingCourse.update(request);
            courseRepository.save(existingCourse);
        }
        return existingCourse;
    }

    private Course createAndSaveNewCourse(final CourseRequestDTO request) {
        Course newCourse = Course.createCourse(request);
        courseRepository.save(newCourse);
        return newCourse;
    }

    private String parseCourseNumber(final String courseName) {
        return courseName.split(" ")[1].replace("코스", "");
    }
}
