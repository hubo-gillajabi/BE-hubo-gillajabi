package com.hubo.gillajabi.place.application.dto.response;

import com.hubo.gillajabi.place.domain.constant.PlaceType;
import com.hubo.gillajabi.place.domain.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String name;
    private PlaceType category;
    private String description;
    private String imageUrl;
    private String mapUrl;
    private Integer courseId;

    public static List<PointDto> from(List<Place> places) {
        List<PointDto> pointDtos = new ArrayList<>();
        for (Place place : places) {
            PointDto pointDto = new PointDto();
            pointDto.setLatitude(BigDecimal.valueOf(place.getLocation()[1]));
            pointDto.setLongitude(BigDecimal.valueOf(place.getLocation()[0]));
            pointDto.setName(place.getName());
            pointDto.setCategory(place.getType());
            pointDto.setDescription(place.getDescription());
            pointDto.setImageUrl(place.getImageUrl());
            pointDto.setMapUrl(place.getMapUrl());
            pointDto.setCourseId(place.getCourseId());
            pointDtos.add(pointDto);
        }
        return pointDtos;
    }
}