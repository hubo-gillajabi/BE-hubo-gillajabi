package com.hubo.gillajabi.course.infrastructure.dto.response;

import com.hubo.gillajabi.course.domain.entity.CourseGps;

import lombok.Getter;

@Getter
public class CourseGpsDto {

    private String gps;

    public CourseGpsDto(CourseGps courseGps){
        this.gps = courseGps.getGps();
    }
}
