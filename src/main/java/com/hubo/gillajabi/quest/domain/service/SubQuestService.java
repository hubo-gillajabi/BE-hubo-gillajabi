package com.hubo.gillajabi.quest.domain.service;

import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.quest.application.dto.response.SubQuestPageResponse;
import com.hubo.gillajabi.quest.domain.entity.MainQuest;
import com.hubo.gillajabi.quest.domain.entity.SubQuest;
import com.hubo.gillajabi.quest.domain.entity.SubQuestStatus;
import com.hubo.gillajabi.quest.infrastructure.dto.projection.SubQuestWithStatusProjection;
import com.hubo.gillajabi.quest.infrastructure.persistence.MainQuestRepository;
import com.hubo.gillajabi.quest.infrastructure.persistence.SubQuestRepository;
import com.hubo.gillajabi.quest.infrastructure.persistence.SubQuestStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubQuestService {

    private final SubQuestRepository subQuestRepository;
    private final MemberRepository memberRepository;


    public SubQuestPageResponse findSubQuestsByMainQuestId(Long mainQuestId, String username) {
        Member member = memberRepository.getEntityByUserName(username);
        List<SubQuestWithStatusProjection> subQuestsPage = subQuestRepository.findByMainQuestIdWithStatus(mainQuestId, member.getId());
        return SubQuestPageResponse.of(mainQuestId, subQuestsPage);
    }
}
