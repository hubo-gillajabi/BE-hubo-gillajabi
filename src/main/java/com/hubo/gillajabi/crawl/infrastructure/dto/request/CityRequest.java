package com.hubo.gillajabi.crawl.infrastructure.dto.request;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CityRequest {

    private String name;

    private Province province;

    private String description;

    public static CityRequest of(final String name, final Province province, final String description) {
        return new CityRequest(name, province, description);
    }

    public static CityRequest of(final String name, final Province province) {
        return new CityRequest(name, province, null);
    }
}

