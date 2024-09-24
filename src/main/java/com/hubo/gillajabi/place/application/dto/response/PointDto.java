package com.hubo.gillajabi.place.application.dto.response;

import com.hubo.gillajabi.place.domain.constant.PlaceType;
import com.hubo.gillajabi.place.domain.entity.PlaceDocument;
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
    private Double latitude;
    private Double longitude;
    private String name;
    private PlaceType category;
    private String description;
    private String imageUrl;
    private String mapUrl;
    private Integer courseId;

    public static List<PointDto> from(List<PlaceDocument> placeDocuments) {
        List<PointDto> pointDtos = new ArrayList<>();
        for (PlaceDocument placeDocument : placeDocuments) {
            PointDto pointDto = new PointDto();
            pointDto.setLongitude(placeDocument.getLocation().getX());
            pointDto.setLatitude(placeDocument.getLocation().getY());
            pointDto.setName(placeDocument.getName());
            pointDto.setCategory(placeDocument.getType());
            pointDto.setDescription(placeDocument.getDescription());
            pointDto.setImageUrl(placeDocument.getImageUrl());
            pointDto.setMapUrl(placeDocument.getMapUrl());
            pointDto.setCourseId(placeDocument.getCourseId());
            pointDtos.add(pointDto);
        }
        return pointDtos;
    }
}