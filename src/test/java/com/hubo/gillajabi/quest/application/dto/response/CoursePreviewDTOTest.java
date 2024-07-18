package com.hubo.gillajabi.quest.application.dto.response;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CoursePreviewDTOTest {

    @Test
    @DisplayName("CoursePreviewDTO.of() 메서드는 CoursePreviewDTO를 생성한다")
    public void one() {
        // given
        Long expectedId = 1L;
        String expectedName = "이름";

        // when
        CoursePreviewDTO result = CoursePreviewDTO.of(expectedId, expectedName);

        // then
        assertEquals(expectedId, result.getId());
        assertEquals(expectedName, result.getName());
    }

    @Test
    @DisplayName("CoursePreviewDTO.toEntity() 메서드는 CoursePreviewDTO를 Course로 변환한다")
    public void two() {
        // given
        Course course = Course.builder()
                .id(1L)
                .originName("이름")
                .build();

        // 실행
        CoursePreviewDTO result = CoursePreviewDTO.toEntity(course);

        // 검증
        assertEquals(course.getId(), result.getId());
        assertEquals(course.getOriginName(), result.getName());
    }

    @Test
    @DisplayName("CoursePreviewDTO.toEntity() 메서드는 null을 반환한다")
    public void three() {
        // when && then
        assertNull(CoursePreviewDTO.toEntity(null));
    }
}
