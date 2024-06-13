package com.hubo.gillajabi.crawl.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusanThemeHandler {

    public String handle() {
        return "Busan 테마 크롤링";
    }
}
