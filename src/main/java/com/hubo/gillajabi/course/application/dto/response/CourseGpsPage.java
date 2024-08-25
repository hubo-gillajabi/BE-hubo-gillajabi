package com.hubo.gillajabi.course.application.dto.response;

import com.hubo.gillajabi.course.infrastructure.dto.response.CourseGpsDto;
import lombok.Getter;

@Getter
public class CourseGpsPage {

    private CourseGpsDto content;

    public CourseGpsPage(CourseGpsDto courseGps) {
        this.content = courseGps;
    }

}
