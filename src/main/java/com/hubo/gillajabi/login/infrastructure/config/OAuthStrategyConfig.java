package com.hubo.gillajabi.login.infrastructure.config;

import com.hubo.gillajabi.login.domain.constant.OauthProvider;
import com.hubo.gillajabi.login.infrastructure.security.GoogleOAuthStrategy;
import com.hubo.gillajabi.login.infrastructure.security.KakaoOAuthStrategy;
import com.hubo.gillajabi.login.infrastructure.security.OAuthStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OAuthStrategyConfig {

    @Bean
    public Map<OauthProvider, OAuthStrategy> oauthStrategies(
            GoogleOAuthStrategy googleOAuthStrategy,
            KakaoOAuthStrategy kakaoOAuthStrategy) {
        Map<OauthProvider, OAuthStrategy> strategies = new HashMap<>();
        strategies.put(OauthProvider.GOOGLE, googleOAuthStrategy);
        strategies.put(OauthProvider.KAKAO, kakaoOAuthStrategy);
        return strategies;
    }
}