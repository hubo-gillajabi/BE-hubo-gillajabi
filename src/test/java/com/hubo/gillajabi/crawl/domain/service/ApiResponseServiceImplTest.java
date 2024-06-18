package com.hubo.gillajabi.crawl.domain.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.client.RestTemplate;

import com.hubo.gillajabi.crawl.domain.entity.CrawlApiResponse;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CrawlApiResponseRepository;

@ExtendWith(MockitoExtension.class)
public class ApiResponseServiceImplTest {

    @Mock
    private CrawlApiResponseRepository crawlApiResponseRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ApiResponseServiceImpl apiResponseServiceImpl;

    @Test
    @DisplayName("URI를 호출하여 API 응답을 가져온다")
    public void uri를_호출에_성공한다() throws Exception {
        // Given
        String apiUrl = "http://test.com";
        String response = "response";
        URI uri = new URI(apiUrl);

        when(crawlApiResponseRepository.findByRequestUrl(apiUrl)).thenReturn(Optional.empty());
        when(restTemplate.getForObject(uri, String.class)).thenReturn(response);

        // When
        String result = apiResponseServiceImpl.fetchApiResponse(uri);

        // Then
        assertEquals(response, result);

        ArgumentCaptor<CrawlApiResponse> captor = ArgumentCaptor.forClass(CrawlApiResponse.class);
        verify(crawlApiResponseRepository, times(1)).save(captor.capture());
        CrawlApiResponse captured = captor.getValue();

        assertEquals(apiUrl, captured.getRequestUrl());
        assertEquals(response, captured.getResponse());
    }

    @Test
    @DisplayName("캐시된 응답이 있을 경우, 캐시된 응답을 반환한다")
    public void 캐시된_응답이_있을_경우_캐시된_응답을_반환_성공() throws Exception {
        // Given
        String apiUrl = "http://test.com";
        String cachedResponse = "cached response";
        URI uri = new URI(apiUrl);

        when(crawlApiResponseRepository.findByRequestUrl(apiUrl)).thenReturn(Optional.of(
                CrawlApiResponse.builder().requestUrl(apiUrl).response(cachedResponse).build()
        ));

        // When
        String result = apiResponseServiceImpl.fetchApiResponse(uri);

        // Then
        assertEquals(cachedResponse, result);
        verify(crawlApiResponseRepository, never()).save(any(CrawlApiResponse.class));
    }

    @Test
    @DisplayName("잘못된 uri일 경우 에러가 발생한다")
    public void 잘못된_uri일_경우_에러가_발생한다() throws Exception {
        // Given
        String apiUrl = "http://test.com";
        URI uri = new URI(apiUrl);

        when(crawlApiResponseRepository.findByRequestUrl(apiUrl)).thenReturn(Optional.empty());
        when(restTemplate.getForObject(uri, String.class)).thenThrow(new RuntimeException());

        // When / Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            apiResponseServiceImpl.fetchApiResponse(uri);
        });

        assertEquals("API 응답을 가져오는 중 문제가 발생했습니다.", exception.getMessage());
        verify(crawlApiResponseRepository, never()).save(any(CrawlApiResponse.class));
    }

}
