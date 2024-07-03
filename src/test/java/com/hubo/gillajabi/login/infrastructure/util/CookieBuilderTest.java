package com.hubo.gillajabi.login.infrastructure.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseCookie;

import static org.junit.jupiter.api.Assertions.*;

public class CookieBuilderTest {

    @Test
    @DisplayName("토큰 쿠키 생성 테스트")
    void 토큰_쿠키_생성_테스트(){
        // given
        String refreshToken = "dummy-refresh-token";
        long maxAge = 3600L;

        // when
        ResponseCookie cookie = CookieBuilder.buildRefreshTokenCookie(refreshToken, maxAge);

        // then
        assertEquals("refresh-token", cookie.getName());
        assertEquals(refreshToken, cookie.getValue());
        assertEquals(maxAge, cookie.getMaxAge().getSeconds());
        assertTrue(cookie.isHttpOnly());
        assertTrue(cookie.isSecure());
        assertEquals("None", cookie.getSameSite());
        assertEquals("/", cookie.getPath());
    }
}
