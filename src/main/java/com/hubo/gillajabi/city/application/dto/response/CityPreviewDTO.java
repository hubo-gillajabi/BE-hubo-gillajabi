package com.hubo.gillajabi.city.application.dto.response;

import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.constant.Province;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityPreviewDTO {
    private Long id;
    private String name;
    private Province province;

    public static CityPreviewDTO of(Long cityId, String cityName, String province) {
        CityPreviewDTO cityPreviewDTO = new CityPreviewDTO();
        cityPreviewDTO.setId(cityId);
        cityPreviewDTO.setName(cityName);
        cityPreviewDTO.setProvince(Province.valueOf(province));
        return cityPreviewDTO;
    }

    public static CityPreviewDTO toEntity(City city) {
        if (city == null) {
            return null;
        }
        return CityPreviewDTO.of(city.getId(), city.getName(), city.getProvince().name());
    }
}