package com.hubo.gillajabi.image.domain.constant;

import com.hubo.gillajabi.image.infrastructure.exception.ImageException;
import com.hubo.gillajabi.image.infrastructure.exception.ImageExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImageTypeTest {

    @Test
    @DisplayName("JPG 이미지 타입이 잘 get되는지 확인한타")
    void JPG_이미지_타입이_잘_가져와지는지_확인() {
        // given
        ImageType imageType = ImageType.JPG;

        // when
        String mimeType = imageType.getMimeType();

        // then
        assert mimeType.equals("image/jpeg");
    }

    @Test
    @DisplayName("JPEG 이미지 타입이 잘 get되는지 확인한타")
    void JPEG_이미지_타입이_잘_가져와지는지_확인() {
        // given
        ImageType imageType = ImageType.JPEG;

        // when
        String mimeType = imageType.getMimeType();

        // then
        assert mimeType.equals("image/jpeg");
    }

    @Test
    @DisplayName("PNG 이미지 타입이 잘 get되는지 확인한타")
    void PNG_이미지_타입이_잘_가져와지는지_확인() {
        // given
        ImageType imageType = ImageType.PNG;

        // when
        String mimeType = imageType.getMimeType();

        // then
        assert mimeType.equals("image/png");
    }

    @Test
    @DisplayName("잘못된 이미지 타입이 들어왔을 때 예외가 발생하는지 확인한다")
    void 잘못된_이미지_타입이_들어왔을_때_예외가_발생하는지_확인() {
        // given
        String mimeType = "image/gif";

        // when
        ImageException exception = assertThrows(ImageException.class, () -> {
            ImageType.validate(mimeType);
        });

        // then
        assertEquals(ImageExceptionCode.INVALID_IMAGE_FORMAT.getErrorCode(), exception.getErrorCode());
    }

    @Test
    @DisplayName("빈값이 들어왔을 때 예외가 잘 발생하는지")
    void 빈값이_들어왔을때_예외가_잘_발생하는지() {
        // given
        String mimeType = null;

        // when
        ImageException exception = assertThrows(ImageException.class, () -> {
            ImageType.validate(mimeType);
        });

        // then
        assertEquals(ImageExceptionCode.INVALID_IMAGE_FORMAT.getErrorCode(), exception.getErrorCode());
    }

    @Test
    @DisplayName("올바른 이미지 타입이 들어왔을 때 예외가 발생하지 않는지 확인한다")
    void 올바른_이미지_타입이_들어왔을때_예외가_발생하지_않는지_확인() {
        // given
        String mimeType = "image/jpeg";

        // when & then
        assertDoesNotThrow(() -> {
            ImageType.validate(mimeType);
        });
    }
}
