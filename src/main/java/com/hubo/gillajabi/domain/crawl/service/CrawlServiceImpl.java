package com.hubo.gillajabi.domain.crawl.service;


import com.hubo.gillajabi.domain.crawl.dto.type.CityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrawlServiceImpl implements CrawlService {

    private final DuruCrawlStrategy duruCrawlStrategy;
    private final BusanCrawlStrategy busanCrawlStrategy;

    @Override
    public String getService(CityType cityType) {
        return switch (cityType) {
            case DURU -> duruCrawlStrategy.crawl();
            case BUSAN -> busanCrawlStrategy.crawl();
        };
    }
}


