package com.hubo.gillajabi.image.infrastructure.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImageUrlBuilderTest {

    @Test
    @DisplayName("build 메서드가 올바른 URL을 생성하는지 테스트")
    void testBuild() {
        // given
        String cloudfrontUrl = "http://test.cloudfront.net";

        // when
        String resultUrl = ImageUrlBuilder.build(cloudfrontUrl);

        // then
        assertNotNull(resultUrl);
        assertTrue(resultUrl.startsWith(cloudfrontUrl + "/"));
    }
}
