package com.hubo.gillajabi.crawl.infraStructure.util.helper;

import com.hubo.gillajabi.crawl.infrastructure.config.ApiProperties;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ValidatableResponse;
import com.hubo.gillajabi.crawl.infrastructure.util.helper.CrawlApiBuilderHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrawlApiBuilderHelperTest {

    private CrawlApiBuilderHelper crawlApiBuilderHelper;

    private ApiProperties apiProperties;

    @Mock
    private ValidatableResponse response;

    private static final String ENDPOINT_PATH = "testEndpoint";
    private static final String SITE_URL = "http://example.com/api/{}?serviceKey={serviceKey}&numOfRows={numOfRows}&pageNo={pageNo}";
    private static final String SERVICE_KEY = "testServiceKey";
    private static final int PAGE_NO = 1;
    private static final int NUM_OF_ROWS = 10;

    @BeforeEach
    public void setUp() {
        crawlApiBuilderHelper = new CrawlApiBuilderHelper();

        apiProperties = new ApiProperties();
        apiProperties.setSiteUrl(SITE_URL);
        apiProperties.setEncoding(SERVICE_KEY);
    }

    @Test
    @DisplayName("buildUri 메서드 호출 시, 올바르게 URI 반환")
    public void 유효한_입력일시_올바른_URI_반환() throws URISyntaxException {
        // given 유효한 입력 값
        URI expectedUri = new URI("http://example.com/api/testEndpoint?serviceKey=testServiceKey&numOfRows=10&pageNo=1");

        // when
        URI resultUri = crawlApiBuilderHelper.buildUri(ENDPOINT_PATH, apiProperties, PAGE_NO, NUM_OF_ROWS);

        // then: 결과 URI가 예상 URI와 일치하는지 확인
        assertEquals(expectedUri, resultUri);
    }

    @Test
    @DisplayName("잘못된 변수로 buildUri 메서드 호출 시, URI 생성 실패")
    public void 유효하지_않은_입력일시_URI생성실패() {
        // given: 잘못된 siteUrl
        String invalidSiteUrl = "http://[invalid-url]/api/{}?serviceKey={serviceKey}&numOfRows={numOfRows}&pageNo={pageNo}";
        apiProperties.setSiteUrl(invalidSiteUrl);

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            crawlApiBuilderHelper.buildUri(ENDPOINT_PATH, apiProperties, PAGE_NO, NUM_OF_ROWS);
        });

        //then
        String expectedMessage = "URI 생성 실패";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    @DisplayName("validateResponse 메서드 호출 시, response.validate() 메서드 호출")
    public void validateResponse() {
        // given
        doNothing().when(response).validate();

        // when
        crawlApiBuilderHelper.validateResponse(response);

        // then
        verify(response, times(1)).validate();
    }
}
