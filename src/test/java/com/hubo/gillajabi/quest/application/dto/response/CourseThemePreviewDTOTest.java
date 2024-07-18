package com.hubo.gillajabi.quest.application.dto.response;

import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CourseThemePreviewDTOTest {

    @Test
    @DisplayName("CourseThemePreviewDTO.of() 메서드는 CourseThemePreviewDTO를 생성한다")
    public void one() {
        // given
        Long expectedId = 1L;
        String expectedName = "이름";

        // when
        CourseThemePreviewDTO result = CourseThemePreviewDTO.of(expectedId, expectedName);

        // then
        assertEquals(expectedId, result.getId());
        assertEquals(expectedName, result.getName());
    }

    @Test
    @DisplayName("CourseThemePreviewDTO.toEntity() 메서드는 CourseThemePreviewDTO를 CourseTheme로 변환한다")
    public void two() {
        // given
        CourseTheme courseTheme = CourseTheme.builder()
                .id(1L)
                .name("이름")
                .build();

        // 실행
        CourseThemePreviewDTO result = CourseThemePreviewDTO.toEntity(courseTheme);

        // 검증
        assertEquals(courseTheme.getId(), result.getId());
        assertEquals(courseTheme.getName(), result.getName());
    }

    @Test
    @DisplayName("CourseThemePreviewDTO.toEntity() 메서드는 null을 반환한다")
    public void three() {
        // when && then
        assertNull(CourseThemePreviewDTO.toEntity(null));
    }

}
