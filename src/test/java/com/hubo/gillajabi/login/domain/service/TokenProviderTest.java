package com.hubo.gillajabi.login.domain.service;

import com.hubo.gillajabi.login.domain.constant.CustomUserDetails;
import com.hubo.gillajabi.login.domain.entity.MemberAuthentication;
import com.hubo.gillajabi.login.infrastructure.security.TokenProvider;
import com.hubo.gillajabi.member.domain.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TokenProviderTest {

    private TokenProvider tokenProvider;
    private SecretKey secretKey;
    private String secret;
    private final Long accessTokenValidTime = 3600L;
    private final Long refreshTokenValidTime = 7200L;

    // 각 테스트 시 TokenProvider 객체 초기화
    @BeforeEach
    void setUp() {
    // mock secret key
        secret = "kZr7QX4kvWh1+5gkcTO3RNqMCXEWZ1+1GkvDL7wTYXq2H7FKzYMstg06ELxuPvaj\n" +
                "iDrdfncFEH0pZXQphu6y4Q==";
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        tokenProvider = new TokenProvider(secret, accessTokenValidTime, refreshTokenValidTime);
    }

    @Test
    @DisplayName("액세스 토큰 생성 테스트")
    void 액세스_토큰_생성_테스트() {
        // given
        Member member = Member.createByNickName("testuser");
        MemberAuthentication memberAuthentication = MemberAuthentication.createByMember(member);

        // when
        String accessToken = tokenProvider.createToken(memberAuthentication).accessToken();

        // then
        assertThat(accessToken).isNotNull();
    }

    @Test
    @DisplayName("리프레시 토큰 생성 테스트")
    void 리프레시_토큰_생성_테스트() {
        // given
        Member member = Member.createByNickName("testuser");
        MemberAuthentication memberAuthentication = MemberAuthentication.createByMember(member);

        // when
        String refreshToken = tokenProvider.createToken(memberAuthentication).refreshToken();

        // then
        assertThat(refreshToken).isNotNull();
    }

    @Test
    @DisplayName("토큰 유효성 검사 테스트")
    void 토큰_유효성_검사_테스트() {
        // given
        Member member = Member.createByNickName("testuser");
        MemberAuthentication memberAuthentication = MemberAuthentication.createByMember(member);
        String validToken = tokenProvider.createToken(memberAuthentication).accessToken();

        // when
        boolean isValid = tokenProvider.validateToken(validToken);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("유효하지 않은 토큰 예외 테스트")
    void 유효하지_않은_토큰_예외_테스트() {
        // given
        String invalidToken = "invalid.token.string";

        // when
        boolean isValid = tokenProvider.validateToken(invalidToken);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("만료된 토큰 예외 테스트")
    void 만료된_토큰_예외_테스트() {
        // given
        Member member = Member.createByNickName("testuser");
        MemberAuthentication memberAuthentication = MemberAuthentication.createByMember(member);
        String expiredToken = generateExpiredToken(memberAuthentication);

        // when
        boolean isValid = tokenProvider.validateToken(expiredToken);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("액세스 토큰 재발급 테스트")
    void 액세스_토큰_재발급_테스트() {
        // given
        Member member = Member.createByNickName("testuser");
        MemberAuthentication memberAuthentication = MemberAuthentication.createByMember(member);

        // when
        String newAccessToken = tokenProvider.reissuanceAccessToken(memberAuthentication);

        // then
        assertThat(newAccessToken).isNotNull();
    }

    @Test
    @DisplayName("토큰에서 사용자 인증 정보 추출 테스트")
    void 토큰에서_사용자_인증_정보_추출_테스트() {
        // given
        Member member = Member.createByNickName("testuser");
        MemberAuthentication memberAuthentication = MemberAuthentication.createByMember(member);
        String token = tokenProvider.createToken(memberAuthentication).accessToken();
        CustomUserDetails expectedUserDetails = new CustomUserDetails(member.getNickName(), memberAuthentication.getRoleStatus().getAuthority());

        // when
        CustomUserDetails userDetails = tokenProvider.getAuthentication(token);

        // then
        assertThat(userDetails.getUsername()).isEqualTo(expectedUserDetails.getUsername());
        assertThat(userDetails.getAuthorities()).isEqualTo(expectedUserDetails.getAuthorities());
    }

    private String generateExpiredToken(MemberAuthentication memberAuthentication) {
        final Date now = new Date();
        // 테스트를 위해 토큰 만료 시키기
        final Date validity = new Date(now.getTime() - 1000);

        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .subject(memberAuthentication.getMember().getNickName())
                .issuedAt(now)
                .expiration(validity)
                .claim("authorities", memberAuthentication.getRoleStatus().getAuthority())
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

}
