package com.hubo.gillajabi.login.application.presenation;

import com.hubo.gillajabi.login.application.dto.response.AccessTokenResponse;
import com.hubo.gillajabi.login.application.dto.response.TokenResponse;
import com.hubo.gillajabi.login.application.service.LoginService;
import com.hubo.gillajabi.login.infrastructure.util.CookieBuilder;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.domain.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    private static final int COOKIE_TIME = 604800;

    private final LoginService loginService;
    private final MemberService memberService;

    @Operation(summary = "게스트 계정생성", description = "게스트 로그인을 수행합니다.")
    @PostMapping("/guest")
    public ResponseEntity<AccessTokenResponse> login(
            final HttpServletResponse response
    ) {
        final Member guest = memberService.createGuest();
        final TokenResponse tokenResponse = loginService.loginGuest(guest);

        final ResponseCookie cookie = CookieBuilder.buildRefreshTokenCookie(tokenResponse.refreshToken(), COOKIE_TIME);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new AccessTokenResponse(tokenResponse.accessToken())
        );
    }

    @Operation(summary =  "access token 재발급", description = "refresh token을 이용하여 access token을 재발급합니다.")
    @PostMapping("/token")
    public ResponseEntity<AccessTokenResponse> extendLogin(
            @CookieValue("refresh-token") final String refreshToken,
            @RequestHeader("Authorization") @Parameter(hidden = true) final String authorizationHeader
    ) {
        final String renewalRefreshToken = loginService.reissuanceAccessToken(refreshToken, authorizationHeader);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AccessTokenResponse(renewalRefreshToken));
    }

    @PostMapping("/oauth/{provider}")
    public ResponseEntity<String> login(String provider) {
        // TODO : oauth 로그인 구현
        return ResponseEntity.ok(provider);
    }
}
