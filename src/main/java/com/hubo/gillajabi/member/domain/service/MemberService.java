package com.hubo.gillajabi.member.domain.service;

import com.hubo.gillajabi.agreement.infrastructure.dto.AgreementEvent;
import com.hubo.gillajabi.login.domain.constant.OAuthUserInfo;
import com.hubo.gillajabi.login.infrastructure.util.SecurityUtil;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.member.infrastructure.util.GuestNickNameBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hubo.gillajabi.member.domain.entity.Member.createByOAuthInfo;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Member createGuest() {
        Member member = Member.createByNickName(GuestNickNameBuilder.buildNickName());
        memberRepository.save(member);

        return member;
    }

    @Transactional
    public Member getMemberByOAuth(OAuthUserInfo oAuthUserInfo, boolean guest) {
        if (guest) {
            return handleGuestLogin(oAuthUserInfo);
        }

        return handleNormalLogin(oAuthUserInfo);
    }

    /*
    * 게스트 계정으로 로그인한 경우
    * 1. 기존 계정이 있는 경우
    * 2. 기존 계정이 없는 경우
     */
    private Member handleGuestLogin(OAuthUserInfo oAuthUserInfo) {
        return memberRepository.findByEmail(oAuthUserInfo.getEmail())
                .map(existingMember -> {
                    String currentGuestUsername = SecurityUtil.getCurrentUsername();
                    Member guestMember = memberRepository.getEntityByUserName(currentGuestUsername);
                    memberRepository.delete(guestMember); // TODO 확인 필요
                    return updateExistingMember(existingMember, oAuthUserInfo);
                })
                .orElseGet(() -> {
                    String currentGuestUsername = SecurityUtil.getCurrentUsername();
                    Member member = memberRepository.getEntityByUserName(currentGuestUsername);
                    eventPublisher.publishEvent(new AgreementEvent(member));
                    return member;
                });
    }

    private Member updateExistingMember(Member member, OAuthUserInfo oAuthUserInfo) {
        return member.updateMemberInfo(oAuthUserInfo);
    }


    // TODO 만약 탈퇴한 사람이라면?
    // 탈퇴한사람을 다시 회원으로 만들어야 하는 경우 탈퇴한 사람의 정보를 다시 회원으로 만들어야 한다.
    /*
    * 일반 계정으로 로그인한 경우
    * 1. 기존 계정이 있는 경우
    * 2. 기존 계정이 없는 경우
     */
    private Member handleNormalLogin(OAuthUserInfo oAuthUserInfo) {
        return memberRepository.findByEmail(oAuthUserInfo.getEmail())
                .orElseGet(() -> {
                    Member member = createByOAuthInfo(oAuthUserInfo);
                    memberRepository.save(member);
                    eventPublisher.publishEvent(new AgreementEvent(member));
                    return member;
                });
    }
}
