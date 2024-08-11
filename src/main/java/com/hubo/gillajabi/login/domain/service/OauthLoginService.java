package com.hubo.gillajabi.login.domain.service;

import com.hubo.gillajabi.login.domain.constant.OAuthUserInfo;
import com.hubo.gillajabi.login.domain.constant.OauthProvider;
import com.hubo.gillajabi.login.infrastructure.security.OAuthStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OauthLoginService {

    private final Map<OauthProvider, OAuthStrategy> oauthStrategies;

    @Transactional
    public OAuthUserInfo oauthLogin(OauthProvider provider, String accessToken) {
        OAuthStrategy oAuthStrategy = oauthStrategies.get(provider);
        return oAuthStrategy.getUserInfo(accessToken);
    }

}
