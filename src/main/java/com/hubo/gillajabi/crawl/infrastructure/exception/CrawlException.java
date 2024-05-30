package com.hubo.gillajabi.crawl.infrastructure.exception;

import lombok.Getter;

@Getter
public class CrawlException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;

    public CrawlException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}