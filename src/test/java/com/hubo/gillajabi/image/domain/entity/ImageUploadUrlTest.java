package com.hubo.gillajabi.image.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class ImageUploadUrlTest {

    @Test
    @DisplayName("createByUrl 메서드가 올바르게 동작하는지 테스트")
    void testCreateByUrl() {
        // given
        String imageUrl = "http://example.com/image.jpg";

        // when
        ImageUploadUrl imageUploadUrl = ImageUploadUrl.createByUrl(imageUrl);

        // then
        assertNotNull(imageUploadUrl);
        assertEquals(imageUrl, imageUploadUrl.getImageUploadUrl());
        assertNotNull(imageUploadUrl.getExpirationTime());

        // 만료 시간이 현재 시간으로부터 1800초 후인지 확인
        Instant now = Instant.now();
        Instant expirationTime = imageUploadUrl.getExpirationTime();
        long differenceInSeconds = expirationTime.getEpochSecond() - now.getEpochSecond();

        // 지연 시간을 고려
        assertTrue(differenceInSeconds >= 1799 && differenceInSeconds <= 1800);
    }
}
