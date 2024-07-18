package com.hubo.gillajabi.crawl.domain.entity;

import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CityRequest;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseRequest;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseThemeRequest;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseTest {

    public static FixtureMonkey fixtureMonkey = FixtureMonkey.builder().build();

    private ApiCourseResponse.Course createApiResponse() {
        return fixtureMonkey.giveMeBuilder(ApiCourseResponse.Course.class)
                .set("sigun", "경남 김해시")
                .set("crsKorNm", "남파랑길 1코스")
                .set("crsLevel", "1")
                .set("crsDstnc", "5")
                .set("crsTotlRqrmHour", "2")
                .sample();
    }

    private City createCity() {
        CityRequest cityRequest = CityRequest.of("김해시", Province.GYEONGNAM, "김해");
        return City.createCity(cityRequest);
    }

    private static CourseTheme createCourseTheme() {
        CourseThemeRequest courseThemeRequest = fixtureMonkey.giveMeOne(CourseThemeRequest.class);
        return CourseTheme.createCourseTheme(courseThemeRequest);
    }


    @Test
    @DisplayName("createCourse 메서드는 CourseReqeust를 사용한다")
    public void createCourse_유효한_CourseRequest로_Course_객체_생성() {
        // given
        CourseRequest courseRequest = CourseRequest.of(createApiResponse(), createCity(), createCourseTheme());

        // when
        Course course = Course.createCourse(courseRequest);

        // then
        assertEquals("남파랑길 1코스", course.getOriginName());
        assertEquals(5, course.getDistance());
    }

}
