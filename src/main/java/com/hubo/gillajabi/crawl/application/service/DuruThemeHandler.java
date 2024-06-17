package com.hubo.gillajabi.crawl.application.service;


import com.hubo.gillajabi.crawl.application.dto.response.CrawlResponse;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.domain.service.duru.CourseDuruThemeService;
import com.hubo.gillajabi.crawl.domain.service.duru.CrawlDuruServiceImpl;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruThemeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DuruThemeHandler {

    private final CrawlDuruServiceImpl duruCrawlService;
    private final CourseDuruThemeService courseDuruThemeService;

    public CrawlResponse.ThemeResult handle() {
        List<DuruThemeResponse.Theme> responseItems = duruCrawlService.crawlTheme();
        List<CourseTheme> themes = courseDuruThemeService.saveDuruTheme(responseItems);
        return CrawlResponse.ThemeResult.from(themes);
    }
}
