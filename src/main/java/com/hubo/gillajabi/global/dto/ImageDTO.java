package com.hubo.gillajabi.global.dto;

import com.hubo.gillajabi.track.domain.entity.PhotoPoint;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@AllArgsConstructor
public class ImageDTO {
    private String url;

    private BigDecimal latitude;

    private BigDecimal longitude;


    public static ImageDTO createByEntity(PhotoPoint photoPoint) {
        return new ImageDTO(
                photoPoint.getPhotoUrl(),
                photoPoint.getLatitude(),
                photoPoint.getLongitude()
        );
    }
}
