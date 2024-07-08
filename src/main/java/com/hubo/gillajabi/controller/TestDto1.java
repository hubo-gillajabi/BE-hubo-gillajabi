package com.hubo.gillajabi.controller;

import com.hubo.gillajabi.global.ImageUrlProvider;

public class TestDto1 implements ImageUrlProvider {

    String imageUrl;

    @Override
    public String getImageUrl() {
        return imageUrl;
    }
}
