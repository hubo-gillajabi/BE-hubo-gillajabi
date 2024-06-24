package com.hubo.gillajabi.road.domain.service.duru;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.domain.service.duru.RoadCourseDuruService;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CityRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseThemeRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseThemeRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoadCourseDuruServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseThemeRepository courseThemeRepository;

    @InjectMocks
    private RoadCourseDuruService roadCourseDuruService;

    private final static FixtureMonkey fixtureMonkey = FixtureMonkey.create();


    public City createCity() {
        CityRequestDTO cityRequestDTO = fixtureMonkey.giveMeBuilder(CityRequestDTO.class).sample();
        cityRequestDTO.setProvince(Province.GYEONGNAM);
        cityRequestDTO.setName("김해시");

        return City.createCity(cityRequestDTO);
    }

    public ApiCourseResponse.Course createDuruCourseResponseCourse() {
        ApiCourseResponse.Course response = fixtureMonkey.giveMeBuilder(ApiCourseResponse.Course.class).sample();
        response.setCrsKorNm("남해랑길 1코스");
        response.setCrsLevel("1");
        response.setGpxpath("http://example.com");
        response.setCrsCycle("비순환형");
        response.setCrsSummary("남해랑길 1코스 소개");
        response.setCrsTotlRqrmHour("1");
        response.setCrsDstnc("1");
        response.setTravelerinfo("시점: 서울<br>교통편) 버스<br>종점: 부산<br>교통편) 기차");
        response.setSigun("경남 김해시");

        return response;
    }

    @Test
    @DisplayName("DuruCourseResponse.Course를 받아서 새로운 Course객체를 생성하고 저장")
    public void saveDuruCourse() {
        // given
        List<City> cities = new ArrayList<>();
        City city = createCity();
        cities.add(city);

        List<ApiCourseResponse.Course> responseItems = new ArrayList<>();
        ApiCourseResponse.Course response = createDuruCourseResponseCourse();
        responseItems.add(response);

        CourseThemeRequestDTO courseThemeRequestDTO = fixtureMonkey.giveMeBuilder(CourseThemeRequestDTO.class).sample();
        CourseTheme courseTheme = CourseTheme.createCourseTheme(courseThemeRequestDTO);

        when(courseRepository.findByOriginName(any())).thenReturn(Optional.empty());
        when(courseThemeRepository.findByName(any())).thenReturn(Optional.of(courseTheme));
        when(courseRepository.save(any())).thenReturn(Course.createCourse(CourseRequestDTO.of(response, city, courseTheme)));

        // when
        List<Course> courses = roadCourseDuruService.saveDuruCourse(responseItems, cities);

        // then
        assertEquals(1, courses.size());
        verify(courseRepository, times(1)).save(any(Course.class));
    }


}
