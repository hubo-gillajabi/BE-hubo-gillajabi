package com.hubo.gillajabi.course.infrastructure.dto;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParsedCourseData {
    private final Course course;
    private final String gpsPoints;
    private final String elevations;

}