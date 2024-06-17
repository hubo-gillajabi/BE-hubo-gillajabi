package com.hubo.gillajabi.crawl.domain.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseLevelTest {

    @Test
    @DisplayName("레벨 1은 LOW 반환")
    void 두루누비_level_1일_경우() {
        assertEquals(CourseLevel.LOW, CourseLevel.fromValue("1"));
    }

    @Test
    @DisplayName("레벨 2은 MIDDLE 반환")
    void 두루누비_level_2일_경우() {
        assertEquals(CourseLevel.MIDDLE, CourseLevel.fromValue("2"));
    }

    @Test
    @DisplayName("레벨 3은 HIGH 반환")
    void 두루누비_level_3일_경우() {
        assertEquals(CourseLevel.HIGH, CourseLevel.fromValue("3"));
    }

    @Test
    @DisplayName("잘못된 레벨 값 예외 발생")
    void 두루누비_level이_잘못된_값일_경우() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> CourseLevel.fromValue("4"));
        assertEquals("잘못된 레벨 값 : 4", exception.getMessage());
    }
}
