package com.hubo.gillajabi.global.common.service;

import com.hubo.gillajabi.crawl.domain.entity.CrawlApiResponse;

import java.net.URI;
import java.util.Optional;

public interface CrawlService {
    Optional<CrawlApiResponse> findByRequestUrl(String string);

    String fetchApiResponse(URI uri);
}
