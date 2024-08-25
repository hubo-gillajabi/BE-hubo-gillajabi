package com.hubo.gillajabi.course.application.dto.response;

import com.hubo.gillajabi.course.infrastructure.dto.response.CourseElevationDto;
import lombok.Getter;

@Getter
public class CourseElevationPage {

    private CourseElevationDto content;

    public CourseElevationPage(CourseElevationDto page) {
        this.content = page;
    }
}
