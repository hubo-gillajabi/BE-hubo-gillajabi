package com.hubo.gillajabi.quest.domain.service;

import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.quest.application.dto.response.SubQuestPageResponse;
import com.hubo.gillajabi.quest.infrastructure.persistence.SubQuestRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubQuestServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private SubQuestRepository subQuestRepository;

    @InjectMocks
    private SubQuestService subQuestService;

    @Test
    @DisplayName("메인 퀘스트 아이디로 서브 퀘스트 조회 테스트")
    void one(){
        // given
        String username = "testUser";
        Member member =  Member.createByNickName(username);

        when(memberRepository.getEntityByUserName(username)).thenReturn(member);

        // when
        SubQuestPageResponse response = subQuestService.findSubQuestsByMainQuestId(1L, username);

        // then
        assertNotNull(response);
        verify(memberRepository).getEntityByUserName(username);
        verify(subQuestRepository).findByMainQuestIdWithStatus(1L, member.getId());
    }

}
