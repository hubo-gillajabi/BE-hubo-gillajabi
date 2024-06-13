package com.hubo.gillajabi.crawl.infrastructure.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiProperties {
    private String endpoint;
    private String encoding;
    private String decoding;
    private String siteUrl;
}
