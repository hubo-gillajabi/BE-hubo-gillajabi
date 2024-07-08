package com.hubo.gillajabi.global.exception;

import com.hubo.gillajabi.crawl.infrastructure.exception.CrawlException;
import com.hubo.gillajabi.image.infrastructure.exception.ImageException;
import com.hubo.gillajabi.login.infrastructure.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CrawlException.class)
    public ResponseEntity<ExceptionResponse> handleCrawlException(final CrawlException e) {
        log.warn(e.getMessage());

        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ExceptionResponse> handleAuthException(final AuthException e) {
        log.warn(e.getMessage());

        return ResponseEntity.status(e.getHttpStatus())
                .body(new ExceptionResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(ImageException.class)
    public ResponseEntity<ExceptionResponse> handleImageException(final ImageException e) {
        log.warn(e.getMessage());

        return ResponseEntity.status(e.getHttpStatus())
                .body(new ExceptionResponse(e.getErrorCode(), e.getMessage()));
    }
}
