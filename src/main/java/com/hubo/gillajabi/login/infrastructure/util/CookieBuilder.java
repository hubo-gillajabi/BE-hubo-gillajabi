package com.hubo.gillajabi.login.infrastructure.util;

import org.springframework.http.ResponseCookie;

public class CookieBuilder {

    public static ResponseCookie buildRefreshTokenCookie(String refreshToken, long maxAge) {
        return ResponseCookie.from("refresh-token", refreshToken)
                .maxAge(maxAge)
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();
    }
}

