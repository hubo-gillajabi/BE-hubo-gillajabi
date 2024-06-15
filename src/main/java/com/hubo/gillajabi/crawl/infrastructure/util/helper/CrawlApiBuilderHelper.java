package com.hubo.gillajabi.crawl.infrastructure.util.helper;

import com.hubo.gillajabi.crawl.infrastructure.config.ApiProperties;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ValidatableResponse;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class CrawlApiBuilderHelper {

    public URI buildUri(final String endpointPath, final ApiProperties apiProperties, final int pageNo, final int numOfRows) {
        try {
            final String url = apiProperties.getSiteUrl()
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
