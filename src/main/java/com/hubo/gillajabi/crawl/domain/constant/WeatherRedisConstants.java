package com.hubo.gillajabi.crawl.domain.constant;

import com.hubo.gillajabi.city.domain.City;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
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

    public static String makeWeatherKey(final City city, final LocalDate date) {
        return makeWeatherKey(city, date, null);
    }
}
