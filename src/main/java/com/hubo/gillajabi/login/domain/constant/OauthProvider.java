package com.hubo.gillajabi.login.domain.constant;

import com.hubo.gillajabi.login.infrastructure.exception.AuthException;
import com.hubo.gillajabi.login.infrastructure.exception.AuthExceptionCode;

public enum OauthProvider {
    GOOGLE,
    KAKAO;

    public static OauthProvider fromString(String provider) {
        try {
            return valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AuthException(AuthExceptionCode.UNSUPPORTED_AUTH_METHOD);
        }
    }
}
