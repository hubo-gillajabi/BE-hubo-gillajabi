package com.hubo.gillajabi.image.domain.entity;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;

@AllArgsConstructor
@RedisHash(value = "image-upload-key", timeToLive = 600)
public class ImageUploadKey {

    @Id
    private String imageUploadKey;

    private Instant expirationTime;

    public static ImageUploadKey createById(String imageKey) {
        return new ImageUploadKey(imageKey, Instant.now().plusSeconds(600));
    }

}
