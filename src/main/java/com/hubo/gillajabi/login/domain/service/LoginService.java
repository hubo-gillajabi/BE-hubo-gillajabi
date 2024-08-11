package com.hubo.gillajabi.login.domain.service;

import com.hubo.gillajabi.login.application.dto.response.TokenResponse;
import com.hubo.gillajabi.login.domain.constant.OauthProvider;
import com.hubo.gillajabi.login.domain.entity.MemberAuthentication;
import com.hubo.gillajabi.login.domain.entity.RefreshToken;
import com.hubo.gillajabi.login.infrastructure.security.OAuthStrategy;
import com.hubo.gillajabi.login.infrastructure.security.TokenProvider;
import com.hubo.gillajabi.login.infrastructure.exception.AuthException;
import com.hubo.gillajabi.login.infrastructure.exception.AuthExceptionCode;
import com.hubo.gillajabi.login.infrastructure.persistence.MemberAuthenticationRepository;
import com.hubo.gillajabi.login.infrastructure.persistence.RefreshTokenRepository;
import com.hubo.gillajabi.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {


    private final MemberAuthenticationRepository memberAuthenticationRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenResponse loginGuest(final Member guest) {
        final MemberAuthentication memberAuthentication = MemberAuthentication.createByMember(guest);
        memberAuthenticationRepository.save(memberAuthentication);

        final TokenResponse tokenResponse = tokenProvider.createToken(memberAuthentication);
        final RefreshToken refreshToken = RefreshToken.createByTokenAndAuthentication(tokenResponse.refreshToken(), memberAuthentication);
        refreshTokenRepository.save(refreshToken);
        return tokenResponse;
    }

    @Transactional
    public String reissuanceAccessToken(final String refreshTokenRequest, final String authorizationHeader) {
        final String accessToken = tokenProvider.extractAccessToken(authorizationHeader);

        if (tokenProvider.isValidRefreshAndInvalidAccess(refreshTokenRequest, accessToken)) {

            final RefreshToken refreshToken = refreshTokenRepository.findById(refreshTokenRequest)
                    .orElseThrow(() ->new AuthException(AuthExceptionCode.INVALID_TOKEN));

            final MemberAuthentication memberAuthentication = memberAuthenticationRepository.findByMemberId(refreshToken.getMemberId())
                    .orElseThrow(() -> new AuthException(AuthExceptionCode.ACCESS_DENIED));

            return tokenProvider.reissuanceAccessToken(memberAuthentication);
        }

        if (tokenProvider.isValidRefreshAndVaildAccess(refreshTokenRequest, accessToken)) {
            return accessToken;
        }

        throw new AuthException(AuthExceptionCode.INVALID_TOKEN);
    }

    public TokenResponse loginUser(MemberAuthentication memberAuthentication) {
        final TokenResponse tokenResponse = tokenProvider.createToken(memberAuthentication);
        final RefreshToken refreshToken = RefreshToken.createByTokenAndAuthentication(tokenResponse.refreshToken(), memberAuthentication);
        refreshTokenRepository.save(refreshToken);
        return tokenResponse;
    }
}
