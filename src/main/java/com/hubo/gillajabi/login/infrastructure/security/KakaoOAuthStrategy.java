package com.hubo.gillajabi.login.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubo.gillajabi.login.domain.constant.OAuthUserInfo;
import com.hubo.gillajabi.login.domain.constant.OauthProvider;
import com.hubo.gillajabi.login.infrastructure.dto.response.KakaoUserResponse;
import com.hubo.gillajabi.login.infrastructure.exception.AuthException;
import com.hubo.gillajabi.login.infrastructure.exception.AuthExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class KakaoOAuthStrategy implements OAuthStrategy {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public String getAuthUrl() {
        return "https://kapi.kakao.com/v2/user/me";
    }

    /*
     * 카카오 API 참고 https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
     */
    @Override
    public HttpHeaders getHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }


    @Override
    public OAuthUserInfo getUserInfo(String accessToken) {
        HttpHeaders headers = getHeaders(accessToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getAuthUrl(),
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        try {
            KakaoUserResponse kakaoUserResponse = objectMapper.readValue(response.getBody(), KakaoUserResponse.class);

            String email = kakaoUserResponse.getKakaoAccount().getEmail();
            String nickname = kakaoUserResponse.getKakaoAccount().getProfile().getNickname();
            String profileImageUrl = kakaoUserResponse.getKakaoAccount().getProfile().getProfileImageUrl();

            return new OAuthUserInfo(email, nickname, profileImageUrl, OauthProvider.KAKAO);
        } catch (Exception e) {
            throw new AuthException(AuthExceptionCode.CREDENTIALS_EXPIRED);
        }
    }
}
