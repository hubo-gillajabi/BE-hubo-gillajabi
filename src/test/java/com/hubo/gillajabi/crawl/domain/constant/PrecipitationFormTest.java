package com.hubo.gillajabi.crawl.domain.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PrecipitationFormTest {

    @Test
    @DisplayName("강수형태 코드가 0일 경우 없음을 반환")
    void 강수형태_코드가_0일_경우_없음을_반환(){
        assertEquals(PrecipitationForm.NONE, PrecipitationForm.fromCode(0));
    }

    @Test
    @DisplayName("강수형태 코드가 1일 경우 비를 반환")
    void 강수형태_코드가_1일_경우_비를_반환(){
        assertEquals(PrecipitationForm.RAIN, PrecipitationForm.fromCode(1));
    }

    @Test
    @DisplayName("강수형태 코드가 2일 경우 비/눈을 반환")
    void 강수형태_코드가_2일_경우_비눈을_반환(){
        assertEquals(PrecipitationForm.RAIN_AND_SNOW, PrecipitationForm.fromCode(2));
    }

    @Test
    @DisplayName("강수형태 코드가 3일 경우 눈을 반환")
    void 강수형태_코드가_3일_경우_눈을_반환(){
        assertEquals(PrecipitationForm.SNOW, PrecipitationForm.fromCode(3));
    }

    @Test
    @DisplayName("강수형태 코드가 4일 경우 소나기를 반환")
    void 강수형태_코드가_4일_경우_소나기를_반환(){
        assertEquals(PrecipitationForm.SHOWER, PrecipitationForm.fromCode(4));
    }
}
