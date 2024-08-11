package com.hubo.gillajabi.login.domain.service;

import com.hubo.gillajabi.login.domain.constant.OAuthUserInfo;
import com.hubo.gillajabi.login.domain.entity.MemberAuthentication;
import com.hubo.gillajabi.login.infrastructure.persistence.MemberAuthenticationRepository;
import com.hubo.gillajabi.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberAuthenticationService {

    private final MemberAuthenticationRepository memberAuthenticationRepository;

    @Transactional
    public MemberAuthentication getMemberAuthentication(OAuthUserInfo oAuthUserInfo, Member member) {
        MemberAuthentication memberAuthentication = memberAuthenticationRepository.findByMember(member)
                .orElseGet(() -> MemberAuthentication.createByMember(member));

        memberAuthentication.updateProvider(oAuthUserInfo);
        memberAuthenticationRepository.save(memberAuthentication);

        return memberAuthentication;
    }

}
