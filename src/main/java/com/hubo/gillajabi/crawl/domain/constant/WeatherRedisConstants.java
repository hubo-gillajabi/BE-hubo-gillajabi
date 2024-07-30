package com.hubo.gillajabi.crawl.domain.constant;

import com.hubo.gillajabi.city.domain.entity.City;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherRedisConstants {

    private static final String WEATHER_API_RESPONSE = "weather:";
    private static final String WILD_CARD = "*";

    /**
     * redis key 생성
    * @param city
    * @param date
    * @param baseTime @Nullable
    * @return key
    */
    public static String makeWeatherKey(final City city, final LocalDate date, @Nullable final String baseTime) {
        final String dateStr = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        if (baseTime == null) {
            return WEATHER_API_RESPONSE + city.getName() + ":" + dateStr;
        }
        return WEATHER_API_RESPONSE + city.getName() + ":" + dateStr + ":" + baseTime;
    }

    /**
     * redis key 생성
     * @param city
     * @param date
     * 현재 날짜로 key를 생성합니다
     * ex) 부산 -> weather:부산:20240614
     * @return key
     */
    public static String makeWeatherKey(final City city, final LocalDate date) {
        return makeWeatherKey(city, date, null);
    }

    /**
     * redis key 생성
     * @param city
     *  현재 시간을 반올림한 시간으로 key를 생성합니다
     *  ex) 부산 -> weather:부산:20240614:11:00
     * @return key
     */
    public static String makeWeatherKey(City city) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 시간을 30분 단위로 반올림
        int hour = now.getHour();
        int minute = now.getMinute();
        if (minute >= 30) {
            hour = (hour + 1) % 24;
        }
        minute = 0;

        String timeStr = String.format("%02d%02d", hour, minute);

        return WEATHER_API_RESPONSE + city.getName() + ":" + dateStr + ":" + timeStr;
    }
}
