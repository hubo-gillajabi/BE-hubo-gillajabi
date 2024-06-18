package com.hubo.gillajabi.global.exception;

import com.hubo.gillajabi.crawl.infrastructure.exception.CrawlException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CrawlException.class)
    public ResponseEntity<ExceptionResponse> handleCrawlException(final CrawlException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getErrorCode(), e.getMessage()));
    }
}
