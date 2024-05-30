package com.hubo.gillajabi.crawl.infrastructure.Helper;

import com.hubo.gillajabi.crawl.domain.constant.ApiProperties;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ValidatableResponse;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class ApiHelper {

    public URI buildUri(String endpointPath, ApiProperties apiProperties, int pageNo, int numOfRows) {
        try {
            String url = apiProperties.getSiteUrl()
                    .replace("{}", endpointPath)
                    .replace("{serviceKey}", apiProperties.getEncoding())
                    .replace("{numOfRows}", String.valueOf(numOfRows))
                    .replace("{pageNo}", String.valueOf(pageNo));
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("URI 생성 실패: " + e.getMessage(), e);
        }
    }

    public void validateResponse(ValidatableResponse response) {
        response.validate();
    }
}