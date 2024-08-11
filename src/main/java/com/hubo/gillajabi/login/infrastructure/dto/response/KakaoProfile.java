package com.hubo.gillajabi.login.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoProfile {
    private String nickname;

    @JsonProperty("profile_image_url")
    private String profileImageUrl;
}

