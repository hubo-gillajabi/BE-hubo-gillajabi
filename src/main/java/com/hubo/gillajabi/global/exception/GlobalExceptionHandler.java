package com.hubo.gillajabi.global.exception;

import com.hubo.gillajabi.course.infrastructure.exception.CourseException;
import com.hubo.gillajabi.crawl.infrastructure.exception.CrawlException;
import com.hubo.gillajabi.image.infrastructure.exception.ImageException;
import com.hubo.gillajabi.login.infrastructure.exception.AuthException;
import com.hubo.gillajabi.mail.infrastructure.exception.MailException;
import com.hubo.gillajabi.track.infrastructure.exception.PhotoPointException;
import com.hubo.gillajabi.track.infrastructure.exception.TrackException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(final IllegalArgumentException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(404, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .reduce((message1, message2) -> message1 + ", " + message2)
                .orElse("잘못된 입력입니다.");

        log.warn("유효하지 않은 입력 :  {}", errorMessage);

        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(404, errorMessage));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String errorMessage = "잘못된 요청입니다";
        //TODO : 에러느 필요한 부분만
        String detail = e.getCause() != null ? e.getCause().getMessage() : null;
        log.warn("{} : {}", errorMessage, detail);

        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(404, errorMessage + detail));
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ExceptionResponse> handleMailException(final MailException e) {
        log.warn("메일 전송 오류: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(e.getErrorCode(), e.getErrorMessage()));
    }

    @ExceptionHandler(PhotoPointException.class)
    public ResponseEntity<ExceptionResponse> handlePhotoPointException(final PhotoPointException e) {
        log.warn("포토 포인트 오류 : {} " , e.getMessage());

        return ResponseEntity.status(e.getHttpStatus())
                .body(new ExceptionResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(TrackException.class)
    public ResponseEntity<ExceptionResponse> handleTrackException(final TrackException e) {
        log.warn("트랙 오류 : {} " , e.getMessage());

        return ResponseEntity.status(e.getHttpStatus())
                .body(new ExceptionResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(CourseException.class)
    public ResponseEntity<ExceptionResponse> handleCourseException(final CourseException e) {
        log.warn("코스 오류 : {} " , e.getMessage());

        return ResponseEntity.status(e.getHttpStatus())
                .body(new ExceptionResponse(e.getErrorCode(), e.getMessage()));
    }
}
