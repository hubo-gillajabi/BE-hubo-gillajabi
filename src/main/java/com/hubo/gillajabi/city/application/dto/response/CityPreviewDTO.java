package com.hubo.gillajabi.city.application.dto.response;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityPreviewDTO {
    private Long id;
    private String name;
    private Province province;
}