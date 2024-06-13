package com.hubo.gillajabi.crawl.application.service;


import com.hubo.gillajabi.crawl.domain.constant.CityName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CrawlFacadeService {

    private final DuruCourseHandler duruCourseHandler;
    private final BusanCourseHandler busanCourseHandler;
    private final DuruThemeHandler duruThemeHandler;
    private final BusanThemeHandler busanThemeHandler;


    public String getCourse(final CityName cityName) {
        return switch (cityName) {
            case DURU -> duruCourseHandler.handle();
            case BUSAN -> busanCourseHandler.handle();
        };
    }

    public String getTheme(final CityName cityName) {
        return switch (cityName) {
            case DURU -> duruThemeHandler.handle();
            case BUSAN -> busanThemeHandler.handle();
        };
    }
}


