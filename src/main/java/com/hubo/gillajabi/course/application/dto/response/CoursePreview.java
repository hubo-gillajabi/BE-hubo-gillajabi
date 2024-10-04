package com.hubo.gillajabi.course.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoursePreview {

    private Long id;
    private String name;

    public static CoursePreview of(Long courseId, String courseName) {
        return new CoursePreview(courseId, courseName);
    }
}
