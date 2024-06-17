package com.hubo.gillajabi.crawl.domain.entity;

import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseThemeRequestDTO;
import com.hubo.gillajabi.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public static CourseTheme createCourseTheme(CourseThemeRequestDTO requestDTO) {
        return new CourseTheme(null, requestDTO.getName(), requestDTO.getShortDescription(), requestDTO.getDescription());
    }

    public void update(String themedescs, String linemsg) {
        if (!themedescs.equals(this.description)) {
            this.name = themedescs;
        }
        if (!linemsg.equals(this.shortDescription)) {
            this.shortDescription = linemsg;
        }
    }
}
