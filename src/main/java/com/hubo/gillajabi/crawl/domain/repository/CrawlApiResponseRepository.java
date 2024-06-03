package com.hubo.gillajabi.crawl.domain.repository;

import com.hubo.gillajabi.crawl.domain.entity.CrawlApiResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrawlApiResponseRepository extends JpaRepository<CrawlApiResponse, Long>{

    Optional<CrawlApiResponse> findByRequestUrl(String requestUrl);
}
