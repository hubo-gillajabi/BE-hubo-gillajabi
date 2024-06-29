package com.hubo.gillajabi.crawl.domain.constant;

import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CycleTypeTest {

    @Test
    @DisplayName("순환형은 CYCLE 반환")
    void 두루누비_순환형일경우_CYCLE반환(){
        ApiCourseResponse.Course course = new ApiCourseResponse.Course();
        course.setCrsCycle("순환형");

        assertEquals(CycleType.CYCLE, CycleType.fromValue(course));
    }

    @Test
    @DisplayName("비순환형이면 SINGLE 반환")
    void 두루누비_비순환형일경우_SINGLE반환(){
        ApiCourseResponse.Course course = new ApiCourseResponse.Course();
        course.setCrsCycle("비순환형");

        assertEquals(CycleType.SINGLE, CycleType.fromValue(course));
    }

    @Test
    @DisplayName("잘못된 순환형 값 예외 발생")
    void 두루누비_순환형이_잘못된_값일_경우(){
        ApiCourseResponse.Course course = new ApiCourseResponse.Course();
        course.setCrsCycle("잘못된 값");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> CycleType.fromValue(course));
        assertEquals("순환형, 비순환형 판별 안됨 잘못된 값", exception.getMessage());
    }
}
