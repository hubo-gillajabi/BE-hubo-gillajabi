package com.hubo.gillajabi.track.application.dto.request;

import com.hubo.gillajabi.global.ImageUrlsProvider;

import java.util.List;

public record PhotoPointRequest(List<String> imageUrls) implements ImageUrlsProvider {

    @Override
    public List<String> getImageUrls() {
        return imageUrls;
    }
}