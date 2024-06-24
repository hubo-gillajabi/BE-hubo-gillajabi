package com.hubo.gillajabi.crawl.application.service;


import com.hubo.gillajabi.crawl.application.response.RoadCrawlResponse;
import com.hubo.gillajabi.crawl.domain.constant.CityName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoadCrawlFacadeService {

    private final RoadDuruCourseHandler roadDuruCourseHandler;
    private final RoadCrawlBusanCourseHandler roadCrawlBusanCourseHandler;
    private final RoadDuruThemeHandler roadDuruThemeHandler;
    private final RoadBusanThemeHandler roadBusanThemeHandler;

    public RoadCrawlResponse.CourseResult getCourse(final CityName cityName) {
        return switch (cityName) {
            case DURU -> roadDuruCourseHandler.handle();
            case BUSAN -> roadCrawlBusanCourseHandler.handle();
        };
    }

    public RoadCrawlResponse.ThemeResult getTheme(final CityName cityName) {
        return switch (cityName) {
            case DURU -> roadDuruThemeHandler.handle();
            case BUSAN -> roadBusanThemeHandler.handle();
        };
    }
}




