package com.hubo.gillajabi.login.domain.entity;

import com.hubo.gillajabi.member.domain.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RefreshTokenTest {

    @Test
    @DisplayName("createByTokenAndAuthentication 메서드로 RefreshToken을 올바르게 생성한다")
    void createByTokenAndAuthentication_메서드로_RefreshToken을_올바르게_생성한다() {
        // given
        String token = "refreshToken";
        Long memberId = 1L;

        Member mockMember = mock(Member.class);
        when(mockMember.getId()).thenReturn(memberId);

        MemberAuthentication mockAuthentication = mock(MemberAuthentication.class);
        when(mockAuthentication.getMember()).thenReturn(mockMember);

        // when
        RefreshToken refreshToken = RefreshToken.createByTokenAndAuthentication(token, mockAuthentication);

        // then
        assertEquals(token, refreshToken.getToken());
        assertEquals(memberId, refreshToken.getMemberId());
    }

}
