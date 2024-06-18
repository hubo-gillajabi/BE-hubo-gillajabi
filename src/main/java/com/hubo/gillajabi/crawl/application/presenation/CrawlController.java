package com.hubo.gillajabi.crawl.application.presenation;


import com.hubo.gillajabi.crawl.application.dto.response.CrawlResponse;
import com.hubo.gillajabi.crawl.application.service.CrawlFacadeService;
import com.hubo.gillajabi.crawl.domain.constant.CityName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crawl")
@RequiredArgsConstructor
@Tag(name = "crawl 컨트롤러", description = "관리자 공공데이터 포털 호출 api")
public class CrawlController {

    private final CrawlFacadeService crawlFacadeService;

    @Operation(summary = "전국 길 크롤링 ", description = "만약 해당 정보가 존재한다면 생략합니다.")
    @GetMapping("/courses")
    public ResponseEntity<CrawlResponse.CourseResult> startCrawlingCurse(@Valid @RequestParam CityName cityName) {

        CrawlResponse.CourseResult response = crawlFacadeService.getCourse(cityName);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "전국 길 테마 크롤링", description = "전국 길 테마 크롤링 (ex: 남파랑길)")
    @GetMapping("/themes")
    public ResponseEntity<CrawlResponse.ThemeResult> startCrawlingTheme(@Valid @RequestParam CityName cityName) {

        CrawlResponse.ThemeResult response = crawlFacadeService.getTheme(cityName);

        return ResponseEntity.ok().body(response);
    }
}


