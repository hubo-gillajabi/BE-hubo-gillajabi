package com.hubo.gillajabi.crawl.domain.service.duru;


import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseDetail;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CityRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseThemeRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseDetailRepository;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseDetailDuruServiceTest {

    @Mock
    private CourseDetailRepository courseDetailRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseDetailDuruService courseDetailDuruService;

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.create();

    public DuruCourseResponse.Course giveMeDuruCourseResponse(){
        DuruCourseResponse.Course response = fixtureMonkey.giveMeBuilder(DuruCourseResponse.Course.class).sample();
        response.setCrsKorNm("남해랑길 1코스");
        response.setCrsLevel("1");
        response.setGpxpath("http://example.com");
        response.setCrsCycle("비순환형");
        response.setCrsSummary("남해랑길 1코스 소개");
        response.setCrsTotlRqrmHour("1");
        response.setCrsDstnc("1");
        response.setTravelerinfo("시점: 서울<br>교통편) 버스<br>종점: 부산<br>교통편) 기차");
        return response;
    }


    public CityRequestDTO giveMeCityRequestDTO(){
        CityRequestDTO cityRequestDTO = fixtureMonkey.giveMeBuilder(CityRequestDTO.class).sample();
        cityRequestDTO.setProvince(Province.BUSAN);
        return cityRequestDTO;
    }

    public CourseThemeRequestDTO giveMeCourseThemeRequestDTO(){
        CourseThemeRequestDTO courseThemeRequestDTO = fixtureMonkey.giveMeBuilder(CourseThemeRequestDTO.class).sample();
        courseThemeRequestDTO.setName("남파랑길");
        return courseThemeRequestDTO;
    }

    public CourseRequestDTO giveMeCourseRequestDTO() {
        DuruCourseResponse.Course response = giveMeDuruCourseResponse();
        City city = City.createCity(giveMeCityRequestDTO());
        CourseTheme courseTheme = CourseTheme.createCourseTheme(giveMeCourseThemeRequestDTO());

        return CourseRequestDTO.of(response, city, courseTheme);
    }

    @DisplayName("DuruCourseResponse.Course를 받아서 새로운 CourseDetail객체를 생성하고 저장")
    @Test
    void DuruCourseResponse_Course를_받아서_새로운_CourseDetail객체를_생성하고_저장() {
        // given
        List<Course> courses = new ArrayList<>();
        courses.add(Course.createCourse(giveMeCourseRequestDTO()));

        List<DuruCourseResponse.Course> responseItems = new ArrayList<>();
        responseItems.add(giveMeDuruCourseResponse());

        when(courseRepository.save(any(Course.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(courseDetailRepository.save(any(CourseDetail.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        List<CourseDetail> savedCourseDetails = courseDetailDuruService.saveDuruCourseDetail(responseItems, courses);

        // then
        assertEquals(1, savedCourseDetails.size());

        verify(courseDetailRepository, times(1)).save(any(CourseDetail.class));
        verify(courseRepository, times(1)).save(any(Course.class));
    }
}

