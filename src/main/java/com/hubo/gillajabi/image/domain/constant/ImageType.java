package com.hubo.gillajabi.image.domain.constant;


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
            throw new IllegalArgumentException("허용되지 않는 이미지 형식입니다.");
        }
    }
}