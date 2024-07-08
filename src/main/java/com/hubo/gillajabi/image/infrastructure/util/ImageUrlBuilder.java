package com.hubo.gillajabi.image.infrastructure.util;

import java.util.UUID;

public class ImageUrlBuilder {

    private static final UUID uuid = UUID.randomUUID();

    public static String build(String cloudfrontUrl) {
        return cloudfrontUrl + "/" +  uuid;
    }
}
