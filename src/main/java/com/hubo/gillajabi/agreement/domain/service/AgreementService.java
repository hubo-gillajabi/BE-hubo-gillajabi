package com.hubo.gillajabi.agreement.domain.service;

import com.hubo.gillajabi.agreement.application.dto.AgreementItem;
import com.hubo.gillajabi.agreement.application.dto.request.AgreementRequest;
import com.hubo.gillajabi.agreement.domain.entity.AgreementTerm;
import com.hubo.gillajabi.agreement.domain.entity.MemberAgreement;
import com.hubo.gillajabi.agreement.infrastructure.exception.AgreementException;
import com.hubo.gillajabi.agreement.infrastructure.exception.AgreementExceptionCode;
import com.hubo.gillajabi.agreement.infrastructure.persistence.AgreementTermRepository;
import com.hubo.gillajabi.agreement.infrastructure.persistence.MemberAgreementRepository;
import com.hubo.gillajabi.mail.infrastructure.dto.MailEvent;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.dto.MemberRoleUpdateEvent;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AgreementService {

    private final MemberAgreementRepository memberAgreementRepository;

    private final MemberRepository memberRepository;

    private final ApplicationEventPublisher eventPublisher;

    private final AgreementTermRepository agreementTermRepository;

    @Transactional
    public void updateAgreements(final String username, AgreementRequest request) {
        final Member member = memberRepository.getEntityByUserName(username);

        final List<MemberAgreement> memberAgreements = memberAgreementRepository.findByMemberWithAgreementTerms(member);

        final List<MemberAgreement> updatedAgreements = processAgreementUpdates(memberAgreements, request.getAgreements());

        memberAgreementRepository.saveAll(updatedAgreements);

        eventPublisher.publishEvent(new MailEvent(member, updatedAgreements));

        eventPublisher.publishEvent(new MemberRoleUpdateEvent(member, updatedAgreements));
    }

    private List<MemberAgreement> processAgreementUpdates(List<MemberAgreement> existingAgreements, List<AgreementItem> agreementItems) {
        Map<Long, MemberAgreement> memberAgreementMap = existingAgreements.stream()
                .collect(Collectors.toMap(ma -> ma.getAgreementTerm().getId(), Function.identity()));

        for (AgreementItem item : agreementItems) {
            MemberAgreement agreement = memberAgreementMap.get(item.getTermId());
            if (agreement != null) {
                agreement.updateAgreement(item.isAgreed());
            } else {
                throw new AgreementException(AgreementExceptionCode.AGREEMENT_NOT_FOUND);
            }
        }

        return new ArrayList<>(memberAgreementMap.values());
    }

    public void getAgreements(final String username) {
        final Member member = memberRepository.getEntityByUserName(username);

        final List<MemberAgreement> memberAgreements = memberAgreementRepository.findByMemberWithAgreementTerms(member);

        eventPublisher.publishEvent(new MailEvent(member, memberAgreements));
    }

    @Transactional
    public void createAgreements(final Member user) {
        List<AgreementTerm> agreementTerms = agreementTermRepository.findAll();

        List<MemberAgreement> memberAgreements = agreementTerms.stream()
                .map(term -> MemberAgreement.create(user, term))
                .collect(Collectors.toList());

        memberAgreementRepository.saveAll(memberAgreements);
    }
}
