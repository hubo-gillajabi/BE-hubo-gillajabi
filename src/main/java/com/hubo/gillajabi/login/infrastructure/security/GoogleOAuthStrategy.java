package com.hubo.gillajabi.login.infrastructure.security;

import com.hubo.gillajabi.login.domain.constant.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GoogleOAuthStrategy implements OAuthStrategy {

    // TODO
    @Override
    public String getAuthUrl() {
        return "https://www.googleapis.com/oauth2/v3/userinfo";
    }

    @Override
    public HttpHeaders getHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        return headers;
    }

    @Override
    public OAuthUserInfo getUserInfo(String accessToken) {
        return null;
    }
}