package com.hubo.gillajabi.controller;


import com.hubo.gillajabi.domain.crawl.dto.type.CityType;
import com.hubo.gillajabi.domain.crawl.service.CrawlService;
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
@Tag(name = "어드민 컨트롤러", description = "관리자 컨트롤러")
public class CrawlController {

    private final CrawlService crawlService;

    @Operation(summary = "전국 길 크롤링 ", description = "duru")
    @GetMapping
    public String startCrawling(@Valid @RequestParam CityType cityType) {

        return crawlService.getService(cityType);

    }

}


