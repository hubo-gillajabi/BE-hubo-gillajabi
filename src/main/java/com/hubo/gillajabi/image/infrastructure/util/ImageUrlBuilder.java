package com.hubo.gillajabi.image.infrastructure.util;

import java.util.UUID;

public class ImageUrlBuilder {

    public static String build(String cloudfrontUrl) {
        return cloudfrontUrl + "/" +  UUID.randomUUID();
    }
}
