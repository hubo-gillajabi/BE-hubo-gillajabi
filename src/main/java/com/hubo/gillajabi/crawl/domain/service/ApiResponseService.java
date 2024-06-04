package com.hubo.gillajabi.crawl.domain.service;

import com.hubo.gillajabi.crawl.domain.entity.CrawlApiResponse;
import com.hubo.gillajabi.crawl.domain.repository.CrawlApiResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApiResponseService {

    private final CrawlApiResponseRepository crawlApiResponseRepository;

    public void saveApiResponse(String apiUrl, String response) {
        CrawlApiResponse crawlApiResponse = CrawlApiResponse.builder()
                .requestUrl(apiUrl)
                .response(response)
                .build();
        crawlApiResponseRepository.save(crawlApiResponse);
    }

    @Transactional(readOnly = true)
    public Optional<CrawlApiResponse> findByRequestUrl(String string) {
        return crawlApiResponseRepository.findByRequestUrl(string);
    }
}
