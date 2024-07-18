package com.hubo.gillajabi.quest.application.dto.response;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import lombok.Getter;
import lombok.Setter;

//TODO : 코스 관련 도메인으로 이관 필요
@Getter
@Setter
public class CoursePreviewDTO {
    private Long id;
    private String name;

    public static CoursePreviewDTO of(final Long courseId, final String courseOriginName) {
        CoursePreviewDTO coursePreviewDTO = new CoursePreviewDTO();
        coursePreviewDTO.setId(courseId);
        coursePreviewDTO.setName(courseOriginName);
        return coursePreviewDTO;
    }

    public static CoursePreviewDTO toEntity(Course course) {
        if(course == null) {
            return null;
        }
        return CoursePreviewDTO.of(course.getId(), course.getOriginName());
    }
}
