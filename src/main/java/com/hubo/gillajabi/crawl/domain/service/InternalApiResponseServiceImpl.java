package com.hubo.gillajabi.crawl.domain.service;

import com.hubo.gillajabi.crawl.domain.entity.CrawlApiResponse;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CrawlApiResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
class InternalApiResponseServiceImpl implements InternalApiResponseService {

    private final CrawlApiResponseRepository crawlApiResponseRepository;
    private final RestTemplate restTemplate;

    @Override
    @Transactional(readOnly = true)
    public Optional<CrawlApiResponse> findByRequestUrl(String string) {
        return crawlApiResponseRepository.findByRequestUrl(string);
    }

    @Override
    @Transactional(noRollbackFor = RuntimeException.class)
    public String fetchApiResponse(final URI uri) {
        String url = uri.toString();
        Optional<String> cachedResponse = getCachedResponse(url);
        return cachedResponse.orElseGet(() -> fetchFromApiAndCache(url, uri));
    }

    private Optional<String> getCachedResponse(String url) {
        return findByRequestUrl(url).map(CrawlApiResponse::getResponse);
    }

    private String fetchFromApiAndCache(String url, URI uri) {
        try {
            String response = restTemplate.getForObject(uri, String.class);
            log.info("API 응답을 가져왔습니다. url: {}", url);
            saveApiResponse(url, response);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("API 응답을 가져오는 중 문제가 발생했습니다.", e);
        }
    }

    private void saveApiResponse(String apiUrl, String response) {
        CrawlApiResponse crawlApiResponse = CrawlApiResponse.builder()
                .requestUrl(apiUrl)
                .response(response)
                .build();
        crawlApiResponseRepository.save(crawlApiResponse);
    }
}
