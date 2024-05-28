package com.hubo.gillajabi.domain.crawl.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiProperties {
    private String endpoint;
    private String encoding;
    private String decoding;
    private String siteUrl;
}
