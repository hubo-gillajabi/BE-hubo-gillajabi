package com.hubo.gillajabi.login.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 604800)
public class RefreshToken {

    @Id
    private String token;

    private Long memberId;

    public static RefreshToken createByTokenAndAuthentication(String token, MemberAuthentication memberAuthentication) {
        return new RefreshToken(token, memberAuthentication.getMember().getId());
    }
}