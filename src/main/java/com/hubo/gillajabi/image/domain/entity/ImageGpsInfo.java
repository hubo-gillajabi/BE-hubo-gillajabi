package com.hubo.gillajabi.image.domain.entity;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@RedisHash(value = "image-gps-info", timeToLive = 1800)
@Getter
@ToString
public class ImageGpsInfo {

    private static final String GPS_INFO_KEY = "image-gps-info:";

    @Id
    private String imageUploadUrl;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private LocalDateTime createdTime;

    public static ImageGpsInfo createByGpsInfo(final String imageUploadUrl, final BigDecimal latitude, final BigDecimal longitude, final LocalDateTime createdTime) {
        return new ImageGpsInfo(imageUploadUrl, latitude, longitude, createdTime);
    }

    public static String getGpsInfoKey(final String imageUploadUrl) {
        return GPS_INFO_KEY + imageUploadUrl;
    }

}
