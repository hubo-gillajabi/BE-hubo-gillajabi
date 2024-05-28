package com.hubo.gillajabi.domain.crawl.service;

import com.hubo.gillajabi.domain.crawl.config.RoadProperties;
import com.hubo.gillajabi.domain.crawl.dto.type.ApiProperties;
import com.hubo.gillajabi.domain.crawl.dto.type.CityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusanCrawlStrategy implements CrawlStrategy {
    private final RoadProperties roadProperties;

    @Override
    public String crawl() {
        return null;

    }
}
