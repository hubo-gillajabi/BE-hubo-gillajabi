package com.hubo.gillajabi.login.infrastructure.security;

import com.hubo.gillajabi.login.domain.constant.OAuthUserInfo;
import org.springframework.http.HttpHeaders;


public interface OAuthStrategy {
    String getAuthUrl();

    HttpHeaders getHeaders(String accessToken);

    OAuthUserInfo getUserInfo(String accessToken);
}