package com.hubo.gillajabi.quest.application.dto.response;


import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import lombok.Getter;
import lombok.Setter;

//TODO : 코스 관련 도메인으로 이관 필요
@Getter
@Setter
public class CourseThemePreviewDTO {
    private Long id;
    private String name;

    public static CourseThemePreviewDTO of(final Long courseThemeId, final String courseThemeName) {
        CourseThemePreviewDTO courseThemePreviewDTO = new CourseThemePreviewDTO();
        courseThemePreviewDTO.setId(courseThemeId);
        courseThemePreviewDTO.setName(courseThemeName);
        return courseThemePreviewDTO;
    }


    public static CourseThemePreviewDTO toEntity(CourseTheme courseTheme) {
        if (courseTheme == null) {
            return null;
        }
        return CourseThemePreviewDTO.of(courseTheme.getId(), courseTheme.getName());
    }
}
