package com.hubo.gillajabi.admin.domain.entity;

import com.hubo.gillajabi.login.domain.constant.OauthProvider;
import com.hubo.gillajabi.login.domain.entity.MemberAuthentication;
import com.hubo.gillajabi.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Admin {

    @Id
    private String id;

    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_authentication_id")
    private MemberAuthentication memberAuthentication;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
