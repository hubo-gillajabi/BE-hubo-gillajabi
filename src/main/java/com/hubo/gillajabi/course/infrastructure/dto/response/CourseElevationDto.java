package com.hubo.gillajabi.course.infrastructure.dto.response;

import com.hubo.gillajabi.course.domain.entity.CourseElevation;
import lombok.Getter;

@Getter
public class CourseElevationDto {

    private String elevation;

    public CourseElevationDto(CourseElevation courseElevation){
        this.elevation = courseElevation.getElevation();
    }
}
