package com.hubo.gillajabi.member.domain.service;

import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.member.infrastructure.util.GuestNickNameBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createGuest() {
        Member member = Member.createByNickName(GuestNickNameBuilder.buildNickName());
        memberRepository.save(member);

        return member;
    }
}
