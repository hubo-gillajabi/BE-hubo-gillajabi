package com.hubo.gillajabi.crawl.application.service;


import com.hubo.gillajabi.crawl.application.dto.response.CrawlResponse;
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

    public CrawlResponse.CourseResult getCourse(final CityName cityName) {
        return switch (cityName) {
            case DURU -> duruCourseHandler.handle();
            case BUSAN -> busanCourseHandler.handle();
        };
    }

    public CrawlResponse.ThemeResult getTheme(final CityName cityName) {
        return switch (cityName) {
            case DURU -> duruThemeHandler.handle();
            case BUSAN -> busanThemeHandler.handle();
        };
    }
}




