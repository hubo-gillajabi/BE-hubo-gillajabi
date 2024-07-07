package com.hubo.gillajabi.image.infrastructure.util;

import java.util.UUID;

public class ImageKeyBuilder {

    private static final UUID uuid = UUID.randomUUID();

    public static String build() {
        return uuid.toString();
    }
}
