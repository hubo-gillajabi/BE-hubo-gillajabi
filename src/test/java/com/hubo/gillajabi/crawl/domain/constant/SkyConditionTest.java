package com.hubo.gillajabi.crawl.domain.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SkyConditionTest {

    @Test
    @DisplayName("하늘상태 코드 1은 CLEAR 반환")
    void 하늘상태_코드_1일_경우() {
        assertEquals(SkyCondition.CLEAR, SkyCondition.fromCode(1));
    }

    @Test
    @DisplayName("하늘상태 코드 2은 PARTLY_CLOUDY 반환")
    void 하늘상태_코드_2일_경우() {
        assertEquals(SkyCondition.PARTLY_CLOUDY, SkyCondition.fromCode(2));
    }

    @Test
    @DisplayName("하늘상태 코드 3은 MOSTLY_CLOUDY 반환")
    void 하늘상태_코드_3일_경우() {
        assertEquals(SkyCondition.MOSTLY_CLOUDY, SkyCondition.fromCode(3));
    }

    @Test
    @DisplayName("하늘상태 코드 4은 CLOUDY 반환")
    void 하늘상태_코드_4일_경우() {
        assertEquals(SkyCondition.CLOUDY, SkyCondition.fromCode(4));
    }

    @Test
    @DisplayName("잘못된 하늘상태 코드 예외 발생")
    void 잘못된_하늘상태_코드일_경우() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SkyCondition.fromCode(5));
        assertEquals("잘못된 하늘 상태 코드 : 5", exception.getMessage());
    }


}
