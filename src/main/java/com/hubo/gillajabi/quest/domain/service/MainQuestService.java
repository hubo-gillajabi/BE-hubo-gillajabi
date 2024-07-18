package com.hubo.gillajabi.quest.domain.service;

import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.quest.application.dto.response.MainQuestPageResponse;
import com.hubo.gillajabi.quest.infrastructure.dto.projection.MainQuestWithSubQuestProjection;
import com.hubo.gillajabi.quest.infrastructure.persistence.MainQuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainQuestService {

    private final MainQuestRepository mainQuestRepository;
    private final MemberRepository memberRepository;

    public MainQuestPageResponse findMainQuestsByCity(final Pageable pageable, final String username) {
        final Member member = memberRepository.getEntityByUserName(username);

        Page<Long> mainQuestIdsPage = mainQuestRepository.findMainQuestIdsWithCityByMember(member.getId(), pageable);

        List<MainQuestWithSubQuestProjection> mainQuests = mainQuestRepository.findMainQuestsByIdsWithCityAndMember(
                mainQuestIdsPage.getContent(), member.getId());

        return MainQuestPageResponse.of(mainQuests, mainQuestIdsPage);
    }

    public MainQuestPageResponse findMainQuestsByTheme(final Pageable pageable, final String username) {
        final Member member = memberRepository.getEntityByUserName(username);

        Page<Long> mainQuestIdsPage = mainQuestRepository.findMainQuestIdsWithCourseThemeByMember(member.getId(), pageable);

        List<MainQuestWithSubQuestProjection> mainQuests = mainQuestRepository.findMainQuestsByIdsWithCourseThemeAndMember(
                mainQuestIdsPage.getContent(), member.getId());

        return MainQuestPageResponse.of(mainQuests, mainQuestIdsPage);
    }

    public MainQuestPageResponse findMainQuestsByCourse(final Pageable pageable, final String username) {
        final Member member = memberRepository.getEntityByUserName(username);

        Page<Long> mainQuestIdsPage = mainQuestRepository.findMainQuestIdsWithCourseByMember(member.getId(), pageable);

        List<MainQuestWithSubQuestProjection> mainQuests = mainQuestRepository.findMainQuestsByIdsWithCourseAndMember(
                mainQuestIdsPage.getContent(), member.getId());

        return MainQuestPageResponse.of(mainQuests, mainQuestIdsPage);
    }
}
