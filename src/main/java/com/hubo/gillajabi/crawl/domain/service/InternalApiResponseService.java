package com.hubo.gillajabi.crawl.domain.service;

import com.hubo.gillajabi.crawl.domain.entity.CrawlApiResponse;

import java.net.URI;
import java.util.Optional;

interface InternalApiResponseService {
    Optional<CrawlApiResponse>findByRequestUrl(String string);

    String fetchApiResponse(URI uri);
}
