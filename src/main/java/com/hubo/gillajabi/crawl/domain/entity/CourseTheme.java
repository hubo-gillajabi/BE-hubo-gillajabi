package com.hubo.gillajabi.crawl.domain.entity;

import com.hubo.gillajabi.course.domain.entity.CourseTag;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseThemeRequest;
import com.hubo.gillajabi.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
@Builder
public class CourseTheme extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(length = 150)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    public static CourseTheme createCourseTheme(CourseThemeRequest requestDTO) {
        return new CourseTheme(null, requestDTO.getName(), requestDTO.getShortDescription(), requestDTO.getDescription());
    }

    public void update(CourseThemeRequest requestDTO) {
        if(!Objects.equals(this.getShortDescription(), requestDTO.getShortDescription())) {
            this.shortDescription = requestDTO.getShortDescription();
        }
        if(!Objects.equals(this.description, requestDTO.getDescription())) {
            this.description = requestDTO.getDescription();
        }
    }
}
