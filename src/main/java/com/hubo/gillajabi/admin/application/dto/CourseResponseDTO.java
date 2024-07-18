package com.hubo.gillajabi.admin.application.dto;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseResponseDTO {

    private Long id;

    private String originName;

    private String courseNumber;

    public static CourseResponseDTO fromEntity(Course course){
        return new CourseResponseDTO(
                course.getId(),
                course.getOriginName(),
                course.getCourseNumber()
        );
    }
}
