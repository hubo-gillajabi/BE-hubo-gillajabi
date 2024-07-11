package com.hubo.gillajabi.crawl.infrastructure.util.helper;

import com.hubo.gillajabi.city.domain.City;
import com.hubo.gillajabi.global.common.dto.ApiProperties;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ValidatableResponse;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CrawlApiBuilderHelper {

    private static final String numOfRows = "100";


    public URI buildUri(final String endpointPath, final ApiProperties apiProperties, final int pageNo) {
        try {
            final String url = apiProperties.getSiteUrl()
                    .replace("{}", endpointPath)
                    .replace("{serviceKey}", apiProperties.getEncoding())
                    .replace("{numOfRows}", numOfRows)
                    .replace("{pageNo}", String.valueOf(pageNo));
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("URI 생성 실패: " + e.getMessage(), e);
        }
    }


    /**
     * MEDIUM_TERM 날씨 API URI 생성 입니다.
     *
     * @param endPointPath  : 어떤 API 를 호출할지
     * @param apiProperties : API 정보
     * @param pageNo        : 페이지 번호
     * @param city          : 도시
     * @param date          : 검색 기준 날짜
     * @return URI
     */
    public URI buildUri(final String endPointPath, final ApiProperties apiProperties, final City city, final int pageNo, final LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = date.format(formatter) + "0600";

        try {
            final String url = apiProperties.getSiteUrl()
                    .replace("{endPath}", endPointPath)
                    .replace("{serviceKey}", apiProperties.getEncoding())
                    .replace("{numOfRows}", numOfRows)
                    .replace("{pageNo}", String.valueOf(pageNo))
                    .replace("{cityCode}", city.getCityCode())
                    .replace("{tmFc}", formattedDate);
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("URI 생성 실패: " + e.getMessage(), e);
        }
    }

    /**
     * current 날씨 API URI 생성 입니다.
     *
     * @param apiProperties
     * @param pageNo        : 페이지 번호
     * @param nx            : x 좌표
     * @param ny            : y 좌표
     * @param baseDate      : 기준 날짜
     * @return
     */
    public URI buildUri(final ApiProperties apiProperties, final int nx, final int ny, final LocalDate baseDate, final int pageNo) {
        try {
            String baseDateStr = baseDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            final String url = apiProperties.getSiteUrl()
                    .replace("{serviceKey}", apiProperties.getEncoding())
                    .replace("{numOfRows}", numOfRows)
                    .replace("{pageNo}", String.valueOf(pageNo))
                    .replace("{baseDate}", baseDateStr)
                    .replace("{nx}", String.valueOf(nx))
                    .replace("{ny}", String.valueOf(ny));
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("URI 생성 실패: " + e.getMessage(), e);
        }
    }


    public void validateResponse(ValidatableResponse response) {
        response.validate();
    }
}
