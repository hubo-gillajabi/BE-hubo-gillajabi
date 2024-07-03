package com.hubo.gillajabi.member.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MemberTest {

    @Test
    @DisplayName("닉네임으로 멤버 생성 테스트")
    void 닉네임으로_멤버_생성_테스트() {
        // given
        String nickName = "test";

        // when
        Member member = Member.createByNickName(nickName);

        // then
        assertThat(member).isNotNull();
        assertThat(member.getNickName()).isEqualTo(nickName);
    }
}
