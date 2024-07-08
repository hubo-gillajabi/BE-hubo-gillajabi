package com.hubo.gillajabi.image.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;

@AllArgsConstructor
@RedisHash(value = "image-upload-url", timeToLive = 1800)
@Getter
public class ImageUploadUrl {

    @Id
    private String imageUploadUrl;

    private Instant expirationTime;

    public static ImageUploadUrl createByUrl(String imageUrl) {
        return new ImageUploadUrl(imageUrl,Instant.now().plusSeconds(1800));
    }

}
