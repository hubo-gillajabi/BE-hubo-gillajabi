package com.hubo.gillajabi.crawl.domain.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MediumTermSkyConditionTest {

    @Test
    @DisplayName("맑음은 CLEAR 반환")
    void 맑음은_CLEAR반환() {
        assertEquals(MediumTermSkyCondition.CLEAR, MediumTermSkyCondition.fromString("맑음"));
    }

    @Test
    @DisplayName("빈 문자열은 CLEAR 반환")
    void 빈문자열은_CLEAR반환() {
        assertEquals(MediumTermSkyCondition.CLEAR, MediumTermSkyCondition.fromString(""));
    }

    @Test
    @DisplayName("구름많음은 MOSTLY_CLOUDY 반환")
    void 구름많음은_MOSTLY_CLOUDY반환() {
        assertEquals(MediumTermSkyCondition.MOSTLY_CLOUDY, MediumTermSkyCondition.fromString("구름많음"));
    }

    @Test
    @DisplayName("구름많고 비는 MOSTLY_CLOUDY_WITH_RAIN 반환")
    void 구름많고_비는_MOSTLY_CLOUDY_WITH_RAIN반환() {
        assertEquals(MediumTermSkyCondition.MOSTLY_CLOUDY_WITH_RAIN, MediumTermSkyCondition.fromString("구름많고 비"));
    }

    @Test
    @DisplayName("구름많고 눈은 MOSTLY_CLOUDY_WITH_SNOW 반환")
    void 구름많고_눈은_MOSTLY_CLOUDY_WITH_SNOW반환() {
        assertEquals(MediumTermSkyCondition.MOSTLY_CLOUDY_WITH_SNOW, MediumTermSkyCondition.fromString("구름많고 눈"));
    }

    @Test
    @DisplayName("구름많고 비/눈은 MOSTLY_CLOUDY_WITH_RAIN_AND_SNOW 반환")
    void 구름많고_비눈은_MOSTLY_CLOUDY_WITH_RAIN_AND_SNOW반환() {
        assertEquals(MediumTermSkyCondition.MOSTLY_CLOUDY_WITH_RAIN_AND_SNOW, MediumTermSkyCondition.fromString("구름많고 비/눈"));
    }

    @Test
    @DisplayName("구름많고 소나기는 MOSTLY_CLOUDY_WITH_SHOWERS 반환")
    void 구름많고_소나기는_MOSTLY_CLOUDY_WITH_SHOWERS반환() {
        assertEquals(MediumTermSkyCondition.MOSTLY_CLOUDY_WITH_SHOWERS, MediumTermSkyCondition.fromString("구름많고 소나기"));
    }

    @Test
    @DisplayName("흐림은 CLOUDY 반환")
    void 흐림은_CLOUDY반환() {
        assertEquals(MediumTermSkyCondition.CLOUDY, MediumTermSkyCondition.fromString("흐림"));
    }

    @Test
    @DisplayName("흐리고 비는 CLOUDY_WITH_RAIN 반환")
    void 흐리고_비는_CLOUDY_WITH_RAIN반환() {
        assertEquals(MediumTermSkyCondition.CLOUDY_WITH_RAIN, MediumTermSkyCondition.fromString("흐리고 비"));
    }

    @Test
    @DisplayName("흐리고 눈은 CLOUDY_WITH_SNOW 반환")
    void 흐리고_눈은_CLOUDY_WITH_SNOW반환() {
        assertEquals(MediumTermSkyCondition.CLOUDY_WITH_SNOW, MediumTermSkyCondition.fromString("흐리고 눈"));
    }

    @Test
    @DisplayName("흐리고 비/눈은 CLOUDY_WITH_RAIN_AND_SNOW 반환")
    void 흐리고_비눈은_CLOUDY_WITH_RAIN_AND_SNOW반환() {
        assertEquals(MediumTermSkyCondition.CLOUDY_WITH_RAIN_AND_SNOW, MediumTermSkyCondition.fromString("흐리고 비/눈"));
    }

    @Test
    @DisplayName("흐리고 소나기는 CLOUDY_WITH_SHOWERS 반환")
    void 흐리고_소나기는_CLOUDY_WITH_SHOWERS반환() {
        assertEquals(MediumTermSkyCondition.CLOUDY_WITH_SHOWERS, MediumTermSkyCondition.fromString("흐리고 소나기"));
    }

    @Test
    @DisplayName("잘못된 값 예외 발생")
    void 잘못된값_예외발생() {
        assertThrows(IllegalArgumentException.class, () -> MediumTermSkyCondition.fromString("잘못된 값"));
    }
}
