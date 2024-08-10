package com.hubo.gillajabi.login.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthUserInfo {
    private String email;
    private String nickname;
    private String profileImageUrl;
    private OauthProvider provider;
}