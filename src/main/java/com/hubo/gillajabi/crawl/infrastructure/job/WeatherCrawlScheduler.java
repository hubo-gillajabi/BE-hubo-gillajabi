package com.hubo.gillajabi.crawl.infrastructure.job;

import com.hubo.gillajabi.crawl.domain.service.WeatherCrawlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherCrawlScheduler {

    private final WeatherCrawlService weatherCrawlService;

    @Scheduled(cron = "0 4 6 * * ?")
    public void scheduleDailyCrawl() {
        log.info("오전 6시 4분에 일일 날씨 크롤링 시작");
        try {
            weatherCrawlService.currentCrawl();
            log.info("현재 날씨 크롤링이 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            log.error("현재 날씨 크롤링 중 오류 발생", e);
        }

        try {
            weatherCrawlService.weatherMediumTermCrawl();
            log.info("중기 날씨 크롤링이 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            log.error("중기 날씨 크롤링 중 오류 발생", e);
        }
    }
}
