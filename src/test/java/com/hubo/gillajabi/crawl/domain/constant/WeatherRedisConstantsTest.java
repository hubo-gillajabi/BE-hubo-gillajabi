package com.hubo.gillajabi.crawl.domain.constant;

import com.hubo.gillajabi.city.domain.City;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CityRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherRedisConstantsTest {

    private City createCity(){
        CityRequest cityRequest = new CityRequest("Seoul", Province.SEOUL, "서울특별시");
        return City.createCity(cityRequest);
    }

    @Test
    @DisplayName("기본 키 생성")
    public void 기본_키_생성() {
        City city = createCity();
        LocalDate date = LocalDate.of(2023, 6, 28);
        String result = WeatherRedisConstants.makeWeatherKey(city, date, null);
        assertThat(result).isEqualTo("weather:Seoul:20230628");
    }

    @Test
    @DisplayName("기본 키 생성 (기본 시간 포함)")
    public void 기본_키_생성_기본_시간_포함() {
        City city = createCity();
        LocalDate date = LocalDate.of(2023, 6, 28);
        String result = WeatherRedisConstants.makeWeatherKey(city, date, "1200");
        assertThat(result).isEqualTo("weather:Seoul:20230628:1200");
    }

    @Test
    @DisplayName("기본 키 생성 (도시 이름 테스트)")
    public void 기본_키_생성_도시_이름_테스트() {
        City city = createCity();
        LocalDate date = LocalDate.of(2023, 6, 28);
        String result = WeatherRedisConstants.makeWeatherKey(city, date, null);
        assertThat(result).isEqualTo("weather:Seoul:20230628");
    }

    @Test
    @DisplayName("기본 키 생성 (날짜 테스트)")
    public void 기본_키_생성_날짜_테스트() {
        City city =  createCity();
        LocalDate date = LocalDate.of(2023, 12, 31);
        String result = WeatherRedisConstants.makeWeatherKey(city, date, null);
        assertThat(result).isEqualTo("weather:Seoul:20231231");
    }

    @Test
    @DisplayName("기본 키 생성 (시간 테스트)")
    public void 기본_키_생성_시간_테스트() {
        City city = createCity();
        LocalDate date = LocalDate.of(2023, 6, 28);
        String result = WeatherRedisConstants.makeWeatherKey(city, date, "0600");
        assertThat(result).isEqualTo("weather:Seoul:20230628:0600");
    }
}
