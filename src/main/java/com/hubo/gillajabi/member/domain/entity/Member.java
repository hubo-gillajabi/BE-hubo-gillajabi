package com.hubo.gillajabi.member.domain.entity;


import com.hubo.gillajabi.global.BaseEntity;
import com.hubo.gillajabi.login.domain.constant.OAuthUserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String nickName;

    @Column(length = 255, unique = true)
    private String email;

    @Column(length = 255)
    private String profileImageUrl;

    @Column(nullable = false)
    private LocalDateTime lastLoginAt;

    public static Member createByNickName(String nickName) {
        return new Member(null, nickName, null, null, LocalDateTime.now());
    }

    public static Member createByOAuthInfo(OAuthUserInfo oAuthUserInfo) {
        return new Member(null, oAuthUserInfo.getNickname(), oAuthUserInfo.getEmail(), oAuthUserInfo.getProfileImageUrl(), LocalDateTime.now());
    }

    public Member updateMemberInfo(OAuthUserInfo oAuthUserInfo) {
        this.nickName = oAuthUserInfo.getNickname();
        this.email = oAuthUserInfo.getEmail();
        this.profileImageUrl = oAuthUserInfo.getProfileImageUrl();
        this.lastLoginAt = LocalDateTime.now();

        return this;
    }
}
