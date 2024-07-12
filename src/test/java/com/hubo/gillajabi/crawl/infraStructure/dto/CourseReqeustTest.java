package com.hubo.gillajabi.crawl.infraStructure.dto;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CityRequest;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseRequest;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseThemeRequest;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseReqeustTest {

    public static FixtureMonkey fixtureMonkey = FixtureMonkey.builder().build();

    private ApiCourseResponse.Course createApiResponse() {
        return fixtureMonkey.giveMeBuilder(ApiCourseResponse.Course.class)
                .set("routeIdx", "1")
                .set("crsIdx", "1")
                .set("crsKorNm", "남파랑길 1코스")
                .set("crsDstnc", "5")
                .set("crsTotlRqrmHour", "2")
                .set("crsLevel", "1")
                .set("crsCycle", "비순환형")
                .set("crsContents", "남파랑길 1코스는 ...")
                .set("crsSummary", "남파랑길 1코스는 ...")
                .set("sigun", "경남 김해시")
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
    @DisplayName("createCourse에서 잘못된 코스 이름이 들어왔을 때 IllegalArgumentException 발생")
    public void createCourse_잘못된_코스_이름_입력시_IllegalArgumentException_발생() {
        // given
        ApiCourseResponse.Course apiCourseResponse = createApiResponse();
        apiCourseResponse.setCrsKorNm("잘못된이름");

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CourseRequest.of(apiCourseResponse, createCity(), createCourseTheme());
        });

        // then
        String expectedMessage = "잘못된 코스 이름 : " + apiCourseResponse.getCrsKorNm();
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("createCourse에서 올바른 코스 이름이 들어왔을때 올바르게 통과")
    public void createCourse_올바른_코스_이름_입력시(){
        // given
        ApiCourseResponse.Course apiCourseResponse = createApiResponse();
        apiCourseResponse.setCrsKorNm("남파랑길 1코스");

        // when
        CourseRequest courseRequest = CourseRequest.of(apiCourseResponse, createCity(), createCourseTheme());

        // then
        assertTrue(courseRequest.getCourseName().equals(apiCourseResponse.getCrsKorNm()));
    }
}
