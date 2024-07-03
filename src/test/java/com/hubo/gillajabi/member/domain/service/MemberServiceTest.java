package com.hubo.gillajabi.member.domain.service;

import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.member.infrastructure.util.GuestNickNameBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;


    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("게스트 게정 생성 테스트")
    public void 게스트_계정_생성_테스트() {
        // given
        Member testMember = Member.createByNickName("Guest123");

        try (MockedStatic<GuestNickNameBuilder> mockedStatic = mockStatic(GuestNickNameBuilder.class)) {
            mockedStatic.when(GuestNickNameBuilder::buildNickName).thenReturn("Guest123");
            when(memberRepository.save(any(Member.class))).thenReturn(testMember);

            // when
            Member result = memberService.createGuest();

            // then
            verify(memberRepository, times(1)).save(any(Member.class));
            assertEquals("Guest123", result.getNickName());
        }
    }
}
