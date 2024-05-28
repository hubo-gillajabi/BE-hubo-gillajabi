package com.hubo.gillajabi.domain.crawl.service;

//import com.hubo.gillajabi.domain.crawl.dto.RoadConfig;

import com.hubo.gillajabi.domain.crawl.config.RoadProperties;
import com.hubo.gillajabi.domain.crawl.dto.type.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DuruCrawlStrategy implements CrawlStrategy {
    private final RestTemplate restTemplate;
    private final RoadProperties roadProperties;

    @Override
    public String crawl() {
        ApiProperties config = null;
        return null;
    }
}
