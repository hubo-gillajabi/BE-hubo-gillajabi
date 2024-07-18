package com.hubo.gillajabi.admin.application.dto;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.city.domain.entity.City;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CityResponseDTO {
    private Long id;
    private String name;
    private Province province;

    public static CityResponseDTO fromEntity(City city) {
        return new CityResponseDTO(
                city.getId(),
                city.getName(),
                city.getProvince()
        );
    }
}
