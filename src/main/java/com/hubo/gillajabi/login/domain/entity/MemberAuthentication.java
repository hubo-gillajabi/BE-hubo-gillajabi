package com.hubo.gillajabi.login.domain.entity;

import com.hubo.gillajabi.global.BaseEntity;
import com.hubo.gillajabi.login.domain.constant.OauthProvider;
import com.hubo.gillajabi.login.domain.constant.RoleStatus;
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
public class MemberAuthentication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    @Enumerated(EnumType.STRING)
    private OauthProvider oauthProvider;

    @Column(nullable = false)
    private String providerId;

    @Column
    @Enumerated(EnumType.STRING)
    private RoleStatus roleStatus;

    public static MemberAuthentication createByMember(Member member) {
        return new MemberAuthentication(null, member, null, null, RoleStatus.GUEST);
    }

    public void updateRoleStatus(boolean allAgreed) {
        this.roleStatus = allAgreed ? RoleStatus.USER : RoleStatus.GUEST;
    }
}
