package com.hubo.gillajabi.login.application.service;

import com.hubo.gillajabi.agreement.domain.entity.MemberAgreement;
import com.hubo.gillajabi.login.domain.constant.RoleStatus;
import com.hubo.gillajabi.login.domain.entity.MemberAuthentication;
import com.hubo.gillajabi.login.infrastructure.persistence.MemberAuthenticationRepository;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.exception.MemberException;
import com.hubo.gillajabi.member.infrastructure.exception.MemberExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRoleService {

    private final MemberAuthenticationRepository memberAuthenticationRepository;

    @Transactional
    public boolean updateRole(Member member, List<MemberAgreement> memberAgreements) {
        MemberAuthentication authentication = memberAuthenticationRepository.findByMember(member)
                .orElseThrow(() -> new MemberException(MemberExceptionCode.MEMBER_NOT_FOUND));

        boolean allAgreed = memberAgreements.stream().allMatch(MemberAgreement::getIsAgreed);

        authentication.updateRoleStatus(allAgreed);
        memberAuthenticationRepository.save(authentication);

        return true;
    }
}
