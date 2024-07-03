package com.hubo.gillajabi.login.domain.entity;

import com.hubo.gillajabi.login.domain.constant.RoleStatus;
import com.hubo.gillajabi.member.domain.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MemberAuthenticationTest {

    @Test
    @DisplayName("멤버로 MemberAuthentication 생성 테스트 ")
    void 멤버로_MemberAuthentication_생성_테스트() {
        // given
        Member member = Member.createByNickName("test");

        // when
        MemberAuthentication memberAuthentication = MemberAuthentication.createByMember(member);

        // then
        assertThat(memberAuthentication).isNotNull();
        assertThat(memberAuthentication.getRoleStatus()).isEqualTo(RoleStatus.GUEST);
        assertThat(memberAuthentication.getMember()).isEqualTo(member);
    }
}
