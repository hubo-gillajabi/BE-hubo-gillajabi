package com.hubo.gillajabi.crawl.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.AbstractApiResponse;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ValidatableResponse;
import com.hubo.gillajabi.crawl.infrastructure.exception.CrawlException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrimaryCrawlingService {

    private final InternalApiResponseService responseCrawlService;
    private final ObjectMapper objectMapper;

    @FunctionalInterface
    public interface CrawlPageFunction<T> {
        AbstractApiResponse<T> crawlPage(int pageNo) throws Exception;
    }

    public <T> List<T> crawlItems(final CrawlPageFunction<T> crawlPageFunction) {
        int pageNo = 1;
        List<T> allItems = new ArrayList<>();
        while (true) {
            try {
                AbstractApiResponse<T> response = crawlPageFunction.crawlPage(pageNo);
                List<T> items = extractItems(response);  // 데이터 추출
                if (isLastPage(items)) {
                    return allItems;
                }
                allItems.addAll(items);
                pageNo++;
            } catch (Exception e) {
                throw new CrawlException(500, "공공데이터 호출 중 예기치 못한 오류 발생 :" + e.getMessage());
            }
        }
    }

    public <T extends ValidatableResponse> T crawlPage(Class<T> responseType, URI uri) {
        try {
            final String response = responseCrawlService.fetchApiResponse(uri);
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            return objectMapper.readValue(response, responseType);
        } catch (JsonProcessingException e) {
            throw new CrawlException(500, "JSON 처리 중 오류 발생: " + e.getMessage());
        }
    }

    public <T> AbstractApiResponse<T> crawlOnePage(Class<T> itemType, URI uri) {
        try {
            final String response = responseCrawlService.fetchApiResponse(uri);
            JavaType responseType = objectMapper.getTypeFactory().constructParametricType(AbstractApiResponse.class, itemType);

            return objectMapper.readValue(response, responseType);
        } catch (JsonProcessingException e) {
            throw new CrawlException(500, "JSON 처리 중 오류 발생: " + e.getMessage());
        }
    }

    private <T> boolean isLastPage(List<T> items) {
        return items == null || items.isEmpty();
    }

    public <T> List<T> extractItems(AbstractApiResponse<T> response) {
        if (response != null && response.getResponse() != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            return response.getResponse().getBody().getItems().getItem();
        }
        return new ArrayList<>();
    }

    public String fetchApiResponse(URI uri) {
        try {
            return responseCrawlService.fetchApiResponse(uri);
        } catch (Exception e) {
            throw new CrawlException(500, "API 호출 중 예기치 못한 오류 발생: " + e.getMessage());
        }
    }


}