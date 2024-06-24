package com.hubo.gillajabi.road.domain.constant;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProvinceTest {

    @Test
    @DisplayName("서울은 SEOUL 반환")
    void 서울일경우(){
        assertEquals(Province.SEOUL, Province.fromValue("서울"));
    }

    @Test
    @DisplayName("부산은 BUSAN 반환")
    void 부산일경우(){
        assertEquals(Province.BUSAN, Province.fromValue("부산"));
    }

    @Test
    @DisplayName("대구는 DAEGU 반환")
    void 대구일경우(){
        assertEquals(Province.DAEGU, Province.fromValue("대구"));
    }

    @Test
    @DisplayName("인천은 INCHEON 반환")
    void 인천일경우(){
        assertEquals(Province.INCHEON, Province.fromValue("인천"));
    }

    @Test
    @DisplayName("광주는 GWANGJU 반환")
    void 광주일경우(){
        assertEquals(Province.GWANGJU, Province.fromValue("광주"));
    }

    @Test
    @DisplayName("대전은 DAEJEON 반환")
    void 대전일경우(){
        assertEquals(Province.DAEJEON, Province.fromValue("대전"));
    }

    @Test
    @DisplayName("울산은 ULSAN 반환")
    void 울산일경우(){
        assertEquals(Province.ULSAN, Province.fromValue("울산"));
    }

    @Test
    @DisplayName("세종은 SEJONG 반환")
    void 세종일경우(){
        assertEquals(Province.SEJONG, Province.fromValue("세종"));
    }

    @Test
    @DisplayName("경기는 GYEONGGI 반환")
    void 경기일경우(){
        assertEquals(Province.GYEONGGI, Province.fromValue("경기"));
    }

    @Test
    @DisplayName("강원은 GANGWON 반환")
    void 강원일경우(){
        assertEquals(Province.GANGWON, Province.fromValue("강원"));
    }

    @Test
    @DisplayName("충북은 CHUNGBUK 반환")
    void 충북일경우(){
        assertEquals(Province.CHUNGBUK, Province.fromValue("충북"));
    }

    @Test
    @DisplayName("충남은 CHUNGNAM 반환")
    void 충남일경우(){
        assertEquals(Province.CHUNGNAM, Province.fromValue("충남"));
    }

    @Test
    @DisplayName("전북은 JEONBUK 반환")
    void 전북일경우(){
        assertEquals(Province.JEONBUK, Province.fromValue("전북"));
    }

    @Test
    @DisplayName("전남은 JEONNAM 반환")
    void 전남일경우(){
        assertEquals(Province.JEONNAM, Province.fromValue("전남"));
    }

    @Test
    @DisplayName("경북은 GYEONGBUK 반환")
    void 경북일경우(){
        assertEquals(Province.GYEONGBUK, Province.fromValue("경북"));
    }

    @Test
    @DisplayName("경남은 GYEONGNAM 반환")
    void 경남일경우(){
        assertEquals(Province.GYEONGNAM, Province.fromValue("경남"));
    }

    @Test
    @DisplayName("제주는 JEJU 반환")
    void 제주일경우(){
        assertEquals(Province.JEJU, Province.fromValue("제주"));
    }

    @Test
    @DisplayName("잘못된 값은 예외 발생")
    void 잘못된값일경우(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Province.fromValue("잘못된 값"));
        assertEquals("해당 지역명이 enum에 없습니다. 잘못된 값", exception.getMessage());
    }

}
