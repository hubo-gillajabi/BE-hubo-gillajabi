package com.hubo.gillajabi.crawl.application.presenation;

import com.hubo.gillajabi.crawl.domain.service.WeatherCrawlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather/crawl")
@RequiredArgsConstructor
@Tag(name = "Weather crawl 컨트롤러 ", description = "관리자 공공 데이터 포털 호출 api(날씨 관련)")
public class WeatherCrawlController {

    private final WeatherCrawlService weatherCrawlService;

    @Operation(summary = "전국 날씨 크롤링 (당일) ", description = "전국 날씨를 3일치를 들고 옵니다.")
    @PostMapping("/current")
    public ResponseEntity<?> startWeatherCurrentCrawl() {

        weatherCrawlService.currentCrawl();

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "전국 날씨 크롤링 (일주일) ", description = "3일 이후 부터 ~7일까지의 날씨 정보를 크롤링합니다.")
    @PostMapping("/medium-term")
    public ResponseEntity<?> startWeatherMediumTermCrawl() {

        weatherCrawlService.weatherMediumTermCrawl();

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "전국 기상 특보 크롤링 (지금 현재) ", description = "미구현")
    @PostMapping("/weather_alert")
    public ResponseEntity<?> startWeatherAlertCrawl() {

        weatherCrawlService.alertCrawl();

        return ResponseEntity.status(500).body("미구현");
    }

}

