package com.hubo.gillajabi.login.domain.constant;

import com.hubo.gillajabi.login.infrastructure.exception.AuthException;
import com.hubo.gillajabi.login.infrastructure.exception.AuthExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OauthProviderTest {

    @Test
    @DisplayName("google을 GOOGLE 로 올바르게 변환한다")
    public void google을_GOOGLE_로_올바르게_변환한다() {
        // given
        String provider = "google";

        // when
        OauthProvider oauthProvider = OauthProvider.fromString(provider);

        // then
        assertEquals(OauthProvider.GOOGLE, oauthProvider);
    }

    @Test
    @DisplayName("kakao를 KAKAO 로 올바르게 변환한다")
    public void kakao를_KAKAO_로_올바르게_변환한다() {
        // given
        String provider = "kakao";

        // when
        OauthProvider oauthProvider = OauthProvider.fromString(provider);

        // then
        assertEquals(OauthProvider.KAKAO, oauthProvider);
    }

    @Test
    @DisplayName("지원하지 않는 provider를 입력하면 AuthException을 던진다")
    void fromString_withUnsupportedProvider_shouldThrowAuthException() {
        // given
        String provider = "not_supported_provider";

        // when & then
        AuthException authException = assertThrows(AuthException.class,
                () -> OauthProvider.fromString(provider));
        assertEquals(AuthExceptionCode.UNSUPPORTED_AUTH_METHOD.getErrorCode(), authException.getErrorCode());
    }


}
