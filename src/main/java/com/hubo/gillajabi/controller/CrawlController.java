package com.hubo.gillajabi.controller;


import com.hubo.gillajabi.crawl.domain.constant.CityName;
import com.hubo.gillajabi.crawl.application.service.CrawlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/crawl")
@RequiredArgsConstructor
@Tag(name = "crawling 컨트롤러", description = "관리자 공공데이터 포털 호출 api")
public class CrawlController {

    private final CrawlService crawlService;

    @Operation(summary = "전국 길 크롤링 ", description = "만약 해당 정보가 존재한다면 생략합니다.")
    @GetMapping
    public String startCrawlingCurse(@Valid @RequestParam CityName cityName) {

        return crawlService.getCourseService(cityName);
    }

    @Operation(summary = "전국 길 테마 크롤링", description = "전국 길 테마 크롤링 (ex: 남파랑길)")
    @GetMapping("/theme")
    public String startCrawlingTheme(@Valid @RequestParam CityName cityName) {
        return crawlService.getCourseTheme(cityName);
    }
}


