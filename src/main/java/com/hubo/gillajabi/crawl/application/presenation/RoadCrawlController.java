package com.hubo.gillajabi.crawl.application.presenation;


import com.hubo.gillajabi.crawl.application.response.RoadCrawlResponse;
import com.hubo.gillajabi.crawl.application.service.RoadCrawlFacadeService;
import com.hubo.gillajabi.crawl.domain.constant.CityName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/road/crawl")
@RequiredArgsConstructor
@Tag(name = "Road crawl 컨트롤러", description = "관리자 공공데이터 포털 호출 api(길 관련)")
public class RoadCrawlController {

    private final RoadCrawlFacadeService roadCrawlFacadeService;

    @Operation(summary = "전국 길 크롤링 ", description = "만약 해당 정보가 존재한다면 생략합니다.")
    @PostMapping("/courses")
    public ResponseEntity<RoadCrawlResponse.CourseResult> startCrawlingCurse(@Valid @RequestParam CityName cityName) {

        RoadCrawlResponse.CourseResult response = roadCrawlFacadeService.getCourse(cityName);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "전국 길 테마 크롤링", description = "전국 길 테마 크롤링 (ex: 남파랑길)")
    @PostMapping("/themes")
    public ResponseEntity<RoadCrawlResponse.ThemeResult> startCrawlingTheme(@Valid @RequestParam CityName cityName) {

        RoadCrawlResponse.ThemeResult response = roadCrawlFacadeService.getTheme(cityName);

        return ResponseEntity.ok().body(response);
    }
}


