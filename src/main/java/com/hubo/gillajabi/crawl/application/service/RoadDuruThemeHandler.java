package com.hubo.gillajabi.crawl.application.service;


import com.hubo.gillajabi.crawl.application.response.RoadCrawlResponse;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.domain.service.duru.RoadCourseDuruThemeService;
import com.hubo.gillajabi.crawl.domain.service.duru.RoadCrawlDuruServiceImpl;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiThemeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoadDuruThemeHandler {

    private final RoadCrawlDuruServiceImpl duruCrawlService;
    private final RoadCourseDuruThemeService roadCourseDuruThemeService;

    public RoadCrawlResponse.ThemeResult handle() {
        List<ApiThemeResponse.Theme> responseItems = duruCrawlService.crawlTheme();
        List<CourseTheme> themes = roadCourseDuruThemeService.saveDuruTheme(responseItems);
        return RoadCrawlResponse.ThemeResult.from(themes);
    }
}
