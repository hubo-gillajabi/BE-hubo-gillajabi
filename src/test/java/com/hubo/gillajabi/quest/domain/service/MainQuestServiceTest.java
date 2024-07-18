package com.hubo.gillajabi.quest.domain.service;

import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.quest.application.dto.response.MainQuestPageResponse;
import com.hubo.gillajabi.quest.infrastructure.dto.projection.MainQuestWithSubQuestProjection;
import com.hubo.gillajabi.quest.infrastructure.persistence.MainQuestRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MainQuestServiceTest {

    @Mock
    private MainQuestRepository mainQuestRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MainQuestService mainQuestService;

    private final FixtureMonkey fixtureMonkey = FixtureMonkey.create();

    @Test
    @DisplayName("도시로 필터링 된 메인 퀘스트 조회 테스트")
    void one() {
        // given
        String username = "testUser";
        Member member =  Member.createByNickName(username);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Long> mainQuestIdsPage = new PageImpl<>(fixtureMonkey.giveMe(Long.class, 5));

        when(memberRepository.getEntityByUserName(username)).thenReturn(member);
        when(mainQuestRepository.findMainQuestIdsWithCityByMember(member.getId(), pageable)).thenReturn(mainQuestIdsPage);

        // when
        MainQuestPageResponse response = mainQuestService.findMainQuestsByCity(pageable, username);

        // then
        assertNotNull(response);
        verify(memberRepository).getEntityByUserName(username);
        verify(mainQuestRepository).findMainQuestIdsWithCityByMember(member.getId(), pageable);
        verify(mainQuestRepository).findMainQuestsByIdsWithCityAndMember(mainQuestIdsPage.getContent(), member.getId());
    }

    @Test
    @DisplayName("테마로 필터링 된 메인 퀘스트 조회 테스트")
    void two() {
        // given
        String username = "testUser";
        Member member = Member.createByNickName(username);
        Pageable pageable = fixtureMonkey.giveMeOne(Pageable.class);
        Page<Long> mainQuestIdsPage = new PageImpl<>(fixtureMonkey.giveMe(Long.class, 5));

        when(memberRepository.getEntityByUserName(username)).thenReturn(member);
        when(mainQuestRepository.findMainQuestIdsWithCourseThemeByMember(member.getId(), pageable)).thenReturn(mainQuestIdsPage);

        // when
        MainQuestPageResponse response = mainQuestService.findMainQuestsByTheme(pageable, username);

        // then
        assertNotNull(response);
        verify(memberRepository).getEntityByUserName(username);
        verify(mainQuestRepository).findMainQuestIdsWithCourseThemeByMember(member.getId(), pageable);
        verify(mainQuestRepository).findMainQuestsByIdsWithCourseThemeAndMember(mainQuestIdsPage.getContent(), member.getId());
    }

    @Test
    @DisplayName("코스로 필터링 된 메인 퀘스트 조회 테스트")
    void three() {
        // given
        String username = "testUser";
        Member member = Member.createByNickName(username);
        Pageable pageable = fixtureMonkey.giveMeOne(Pageable.class);
        Page<Long> mainQuestIdsPage = new PageImpl<>(fixtureMonkey.giveMe(Long.class, 5));

        when(memberRepository.getEntityByUserName(username)).thenReturn(member);
        when(mainQuestRepository.findMainQuestIdsWithCourseByMember(member.getId(), pageable)).thenReturn(mainQuestIdsPage);

        // when
        MainQuestPageResponse response = mainQuestService.findMainQuestsByCourse(pageable, username);

        // then
        assertNotNull(response);
        verify(memberRepository).getEntityByUserName(username);
        verify(mainQuestRepository).findMainQuestIdsWithCourseByMember(member.getId(), pageable);
        verify(mainQuestRepository).findMainQuestsByIdsWithCourseAndMember(mainQuestIdsPage.getContent(), member.getId());
    }

}
