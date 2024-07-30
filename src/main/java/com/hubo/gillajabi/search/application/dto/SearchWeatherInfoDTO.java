package com.hubo.gillajabi.search.application.dto;

import com.hubo.gillajabi.crawl.domain.constant.PrecipitationForm;
import com.hubo.gillajabi.crawl.domain.constant.SkyCondition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchWeatherInfoDTO {

    private Float lowestTemperature;

    private Float highestTemperature;

    private PrecipitationForm precipitationForm;

    private SkyCondition skyCondition;

}
