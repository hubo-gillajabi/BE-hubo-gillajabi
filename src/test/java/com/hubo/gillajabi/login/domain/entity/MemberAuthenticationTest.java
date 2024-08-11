package com.hubo.gillajabi.login.domain.entity;

import com.hubo.gillajabi.login.domain.constant.OAuthUserInfo;
import com.hubo.gillajabi.login.domain.constant.OauthProvider;
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

    @Test
    @DisplayName("role 변경 테스트 (약관 동의 여부 시 User로 변경)")
    void role_변경_테스트1() {
        // given
        Member member = Member.createByNickName("test");
        MemberAuthentication memberAuthentication = MemberAuthentication.createByMember(member);

        // when
        memberAuthentication.updateRoleStatus(true);

        // then
        assertThat(memberAuthentication.getRoleStatus()).isEqualTo(RoleStatus.USER);
    }

    @Test
    @DisplayName("role 변경 테스트 (약관 동의 여부 미동의 시 Guest로 변경)")
    void role_변경_테스트2() {
        // given
        Member member = Member.createByNickName("test");
        MemberAuthentication memberAuthentication = MemberAuthentication.createByMember(member);

        // when
        memberAuthentication.updateRoleStatus(false);

        // then
        assertThat(memberAuthentication.getRoleStatus()).isEqualTo(RoleStatus.GUEST);
    }

    @Test
    @DisplayName("OuahUserInfo로 provider변경 테스트")
    void provider_변경_테스트() {
        // given
        Member member = Member.createByNickName("test");
        MemberAuthentication memberAuthentication = MemberAuthentication.createByMember(member);
        OAuthUserInfo oAuthUserInfo = new OAuthUserInfo("google@naver.com", "1k13","profileImage", OauthProvider.GOOGLE);
        // when
        memberAuthentication.updateProvider(oAuthUserInfo);

        // then
        assertThat(memberAuthentication.getOauthProvider()).isEqualTo(OauthProvider.GOOGLE);
    }
}
