package com.hubo.gillajabi.login.infrastructure.security;

import com.hubo.gillajabi.login.application.dto.response.TokenResponse;
import com.hubo.gillajabi.login.domain.constant.CustomUserDetails;
import com.hubo.gillajabi.login.domain.entity.MemberAuthentication;
import com.hubo.gillajabi.login.infrastructure.exception.AuthException;
import com.hubo.gillajabi.login.infrastructure.exception.AuthExceptionCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    private final SecretKey secretKey;

    private final Long accessTokenValidTime;

    private final Long refreshTokenValidTime;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity-time}") Long accessTokenValidTime,
            @Value("${jwt.refresh-token-validity-time}") Long refreshTokenValidTime) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidTime = accessTokenValidTime * 1000;
        this.refreshTokenValidTime = refreshTokenValidTime * 1000;
    }

    public TokenResponse createToken(MemberAuthentication memberAuthentication) {
        return new TokenResponse(
                createAccessToken(memberAuthentication),
                createRefreshToken(memberAuthentication));
    }

    private String createAccessToken(MemberAuthentication memberAuthentication) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + this.accessTokenValidTime);

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

    private String createRefreshToken(MemberAuthentication memberAuthentication) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + this.refreshTokenValidTime);

        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .subject(memberAuthentication.getMember().getNickName())
                .issuedAt(now)
                .expiration(validity)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("토큰 만료됨: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 토큰: " + e.getMessage());
        } catch (MalformedJwtException e) {
            log.info("잘못된 토큰: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.info("토큰이 없음: " + e.getMessage());
        }
        return false;
    }

    public CustomUserDetails getAuthentication(String token) {
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        Claims claims = claimsJws.getPayload();
        String username = claims.getSubject();
        String role = claims.get("authorities", String.class);
        return new CustomUserDetails(username, role);
    }


    public String extractAccessToken(final String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    //TODO
    public boolean isValidRefreshAndInvalidAccess(final String refreshToken, final String accessToken) {
        validateRefreshToken(refreshToken);
        try {
            validateAccessToken(accessToken);
        } catch (ExpiredJwtException e) {
            return true;
        }
        return false;
    }

    private void validateAccessToken(String accessToken) {
        try {
            parseToken(accessToken);
        } catch (final ExpiredJwtException e) {
            throw new AuthException(AuthExceptionCode.TOKEN_EXPIRED);
        } catch (final JwtException | IllegalArgumentException e) {
            throw new AuthException(AuthExceptionCode.INVALID_TOKEN);
        }
    }

    private void validateRefreshToken(String refreshToken) {
        try {
            parseToken(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new AuthException(AuthExceptionCode.TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthException(AuthExceptionCode.INVALID_TOKEN);
        }
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
    }

    public boolean isValidRefreshAndVaildAccess(String refreshToken, String accessToken) {
        try {
            validateRefreshToken(refreshToken);
            validateAccessToken(accessToken);
            return true;
        } catch (final JwtException e) {
            return false;
        }
    }

    public String reissuanceAccessToken(MemberAuthentication memberAuthentication) {
        return createAccessToken(memberAuthentication);
    }
}
