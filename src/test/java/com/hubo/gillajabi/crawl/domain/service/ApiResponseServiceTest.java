package com.hubo.gillajabi.crawl.domain.service;

import com.hubo.gillajabi.crawl.domain.repository.CrawlApiResponseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hubo.gillajabi.crawl.domain.entity.CrawlApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class ApiResponseServiceTest {

    @InjectMocks
    private ApiResponseService apiResponseService;

    @Mock
    private CrawlApiResponseRepository crawlApiResponseRepository;

    @DisplayName("받아온 response가 제대로 저장되는지 확인한다.")
    @Test
    public void testSaveApiResponse() {
        //given
        String apiUrl = "http://test.com";
        String response = "response";

        //when
        apiResponseService.saveApiResponse(apiUrl, response);

        //then
        ArgumentCaptor<CrawlApiResponse> captor = ArgumentCaptor.forClass(CrawlApiResponse.class);
        verify(crawlApiResponseRepository, times(1)).save(captor.capture());
        CrawlApiResponse captured = captor.getValue();

        assertEquals(apiUrl, captured.getRequestUrl());
        assertEquals(response, captured.getResponse());

    }

    @DisplayName("requestUrl로 저장된 response를 찾아온다.")
    @Test
    public void testFindByRequestUrl() {
        //given
        String requestUrl = "http://test.com";
        CrawlApiResponse crawlApiResponse = CrawlApiResponse.builder()
                .requestUrl(requestUrl)
                .build();

        //when
        when(crawlApiResponseRepository.findByRequestUrl(requestUrl)).thenReturn(Optional.of(crawlApiResponse));
        Optional<CrawlApiResponse> result = apiResponseService.findByRequestUrl(requestUrl);

        verify(crawlApiResponseRepository, times(1)).findByRequestUrl(requestUrl);
        assert (result.isPresent());
        assert (result.get().getRequestUrl().equals(requestUrl));
    }
}
