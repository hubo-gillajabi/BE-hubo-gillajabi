package com.hubo.gillajabi.crawl.application.service;


import com.hubo.gillajabi.crawl.domain.service.CourseThemeSerivce;
import com.hubo.gillajabi.crawl.domain.service.CrawlDuruServiceImpl;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruThemeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DuruThemeHandler {

    private final CrawlDuruServiceImpl duruCrawlService;
    private final CourseThemeSerivce courseThemeService;

    public String handle(){
            List<DuruThemeResponse.Theme> responseItems = duruCrawlService.crawlTheme();
            courseThemeService.saveDuruTheme(responseItems);
            return responseItems.size() + "개의 데이터가 저장되었습니다.";
        }
}
