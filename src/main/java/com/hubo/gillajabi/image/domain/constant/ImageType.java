package com.hubo.gillajabi.image.domain.constant;


import com.hubo.gillajabi.image.infrastructure.exception.ImageException;
import com.hubo.gillajabi.image.infrastructure.exception.ImageExceptionCode;

public enum ImageType {
    JPG("image/jpeg"),
    JPEG("image/jpeg"),
    PNG("image/png");

    private final String mimeType;

    ImageType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    private static boolean isValidType(String mimeType) {
        for (ImageType type : values()) {
            if (type.getMimeType().equals(mimeType)) {
                return true;
            }
        }
        return false;
    }

    public static void validate(String mimeType) {
        if (mimeType == null || !isValidType(mimeType)) {
            throw new ImageException(ImageExceptionCode.INVALID_IMAGE_FORMAT);
        }
    }
}