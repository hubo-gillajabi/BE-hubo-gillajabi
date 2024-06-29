package com.hubo.gillajabi.crawl.application.service;


import com.hubo.gillajabi.crawl.application.response.RoadCrawlResponse;
import com.hubo.gillajabi.crawl.domain.constant.CityCrawlName;
import com.hubo.gillajabi.crawl.domain.service.busan.RoadBusanThemeHandler;
import com.hubo.gillajabi.crawl.domain.service.busan.RoadCrawlBusanCourseHandler;
import com.hubo.gillajabi.crawl.domain.service.duru.RoadCourseDuruHandler;
import com.hubo.gillajabi.crawl.domain.service.duru.RoadThemeDuruHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoadCrawlFacadeService {

    private final RoadCourseDuruHandler roadCourseDuruHandler;
    private final RoadCrawlBusanCourseHandler roadCrawlBusanCourseHandler;
    private final RoadThemeDuruHandler roadThemeDuruHandler;
    private final RoadBusanThemeHandler roadBusanThemeHandler;

    public RoadCrawlResponse.CourseResult getCourse(final CityCrawlName cityCrawlName) {
        return switch (cityCrawlName) {
            case DURU -> roadCourseDuruHandler.handle();
            case BUSAN -> roadCrawlBusanCourseHandler.handle();
        };
    }

    public RoadCrawlResponse.ThemeResult getTheme(final CityCrawlName cityCrawlName) {
        return switch (cityCrawlName) {
            case DURU -> roadThemeDuruHandler.handle();
            case BUSAN -> roadBusanThemeHandler.handle();
        };
    }
}




