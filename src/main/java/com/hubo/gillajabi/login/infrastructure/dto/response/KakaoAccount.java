package com.hubo.gillajabi.login.infrastructure.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoAccount {
    private String email;
    private KakaoProfile profile;
}