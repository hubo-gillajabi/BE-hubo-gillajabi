package com.hubo.gillajabi.login.domain.service;

import com.hubo.gillajabi.login.application.dto.response.TokenResponse;
import com.hubo.gillajabi.login.domain.entity.MemberAuthentication;
import com.hubo.gillajabi.login.domain.entity.RefreshToken;
import com.hubo.gillajabi.login.infrastructure.exception.AuthException;
import com.hubo.gillajabi.login.infrastructure.persistence.MemberAuthenticationRepository;
import com.hubo.gillajabi.login.infrastructure.persistence.RefreshTokenRepository;
import com.hubo.gillajabi.login.infrastructure.security.TokenProvider;
import com.hubo.gillajabi.member.domain.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private MemberAuthenticationRepository memberAuthenticationRepository;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Nested
    @DisplayName("게스트 로그인 테스트")
    class guestLoginTest {
        @Test
        @DisplayName("게스트 로그인시 MemberAuthentication가 저장 된다")
        void 게스트_로그인_시_MemberAuthentication이_저장된다() {
            // given
            Member guset = Member.createByNickName("guest");
            TokenResponse mockTokenResponse = mock(TokenResponse.class);
            when(tokenProvider.createToken(any())).thenReturn(mockTokenResponse);

            // when
            loginService.loginGuest(guset);

            // then
            verify(memberAuthenticationRepository, times(1)).save(any(MemberAuthentication.class));
        }

        @Test
        @DisplayName("게스트 로그인시 TokenResponse가 반환된다")
        void 게스트_로그인_시_TokenResponse가_반환된다() {
            // given
            Member guset = Member.createByNickName("guest");
            TokenResponse mockTokenResponse = mock(TokenResponse.class);
            when(tokenProvider.createToken(any())).thenReturn(mockTokenResponse);

            // when
            TokenResponse tokenResponse = loginService.loginGuest(guset);

            // then
            verify(tokenProvider, times(1)).createToken(any());
        }
    }
}

