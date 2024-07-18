package com.hubo.gillajabi.admin.application.dto;

import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseThemeResponseDTO {

    private Long id;

    private String name;

    public static CourseThemeResponseDTO fromEntity(CourseTheme courseTheme) {
        return new CourseThemeResponseDTO(
                courseTheme.getId(),
                courseTheme.getName()
        );
    }

}
