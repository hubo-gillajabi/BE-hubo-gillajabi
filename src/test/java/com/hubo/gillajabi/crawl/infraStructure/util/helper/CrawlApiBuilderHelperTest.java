//package com.hubo.gillajabi.crawl.infraStructure.util.helper;
//
//import com.hubo.gillajabi.city.domain.entity.City;
//import com.hubo.gillajabi.crawl.infrastructure.util.helper.CrawlApiBuilderHelper;
//import com.hubo.gillajabi.global.common.dto.ApiProperties;
//import com.hubo.gillajabi.crawl.infrastructure.dto.response.ValidatableResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CrawlApiBuilderHelperTest {
//
//    private CrawlApiBuilderHelper crawlApiBuilderHelper;
//
//    private ApiProperties apiProperties;
//
//    @Mock
//    private ValidatableResponse response;
//
//    @Mock
//    private City city;
//
//    private static final String ENDPOINT_PATH = "testEndpoint";
//    private static final String SITE_URL = "http://example.com/api/{}?serviceKey={serviceKey}&numOfRows={numOfRows}&pageNo={pageNo}";
//    private static final String SERVICE_KEY = "testServiceKey";
//    private static final int PAGE_NO = 1;
//    private static final String CITY_CODE = "testCityCode";
//    private static final LocalDate DATE = LocalDate.of(2023, 6, 28);
//    private static final int NX = 60;
//    private static final int NY = 127;
//
//    @BeforeEach
//    public void setUp() {
//        crawlApiBuilderHelper = new CrawlApiBuilderHelper();
//
//        apiProperties = new ApiProperties();
//        apiProperties.setSiteUrl(SITE_URL);
//        apiProperties.setEncoding(SERVICE_KEY);
//
//       // when(city.getCityCode()).thenReturn(CITY_CODE);
//    }
//
//    @Test
//    @DisplayName("buildUri 메서드 호출 시, 올바르게 URI 반환")
//    public void 유효한_입력일시_올바른_URI_반환() throws URISyntaxException {
//        // given
//        URI expectedUri = new URI("http://example.com/api/testEndpoint?serviceKey=testServiceKey&numOfRows=100&pageNo=1");
//
//        // when
//        URI resultUri = crawlApiBuilderHelper.buildUri(ENDPOINT_PATH, apiProperties, PAGE_NO);
//
//        // then
//        assertEquals(expectedUri, resultUri);
//    }
//
//    @Test
//    @DisplayName("잘못된 변수로 buildUri 메서드 호출 시, URI 생성 실패")
//    public void 유효하지_않은_입력일시_URI생성실패() {
//        // given
//        String invalidSiteUrl = "http://[invalid-url]/api/{}?serviceKey={serviceKey}&numOfRows={numOfRows}&pageNo={pageNo}";
//        apiProperties.setSiteUrl(invalidSiteUrl);
//
//        // when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            crawlApiBuilderHelper.buildUri(ENDPOINT_PATH, apiProperties, PAGE_NO);
//        });
//
//        //then
//        String expectedMessage = "URI 생성 실패";
//        assertTrue(exception.getMessage().contains(expectedMessage));
//    }
//
//    @Test
//    @DisplayName("validateResponse 메서드 호출 시, response.validate() 메서드 호출")
//    public void validateResponse() {
//        // given
//        doNothing().when(response).validate();
//
//        // when
//        crawlApiBuilderHelper.validateResponse(response);
//
//        // then
//        verify(response, times(1)).validate();
//    }
//
//    @Test
//    @DisplayName("buildUri (MEDIUM_TERM) 메서드 호출 시, 올바르게 URI 반환")
//    public void 유효한_MEDIUM_TERM_입력일시_올바른_URI_반환() throws URISyntaxException {
//        // given
//        String invalidSiteUrl = "https://apis.data.go.kr/1360000/MidFcstInfoService/{endPath}?serviceKey={serviceKey}&pageNo={pageNo}&numOfRows={numOfRows}&dataType=JSON&regId={cityCode}&tmFc={tmFc}
//        apiProperties.setSiteUrl(invalidSiteUrl);
//        // when
//        URI resultUri = crawlApiBuilderHelper.buildUri(ENDPOINT_PATH, apiProperties, city, PAGE_NO, DATE);
//
//        // then
//        assertEquals(expectedUri, resultUri);
//    }
//
//    @Test
//    @DisplayName("buildUri (current 날씨) 메서드 호출 시, 올바르게 URI 반환")
//    public void 유효한_현재_날씨_입력일시_올바른_URI_반환() throws URISyntaxException {
//        // given
//        String baseDateStr = DATE.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        URI expectedUri = new URI("http://example.com/api/testEndpoint?serviceKey=testServiceKey&pageNo=1&numOfRows=100&baseDate=" + baseDateStr + "&nx=" + NX + "&ny=" + NY);
//
//        // when
//        URI resultUri = crawlApiBuilderHelper.buildUri(apiProperties, NX, NY, DATE, PAGE_NO);
//
//        // then
//        assertEquals(expectedUri, resultUri);
//    }
//
//}
