package com.hubo.gillajabi.login.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserResponse {
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;
}

