package com.hubo.gillajabi.mail.infrastructure.dto;

import com.hubo.gillajabi.agreement.domain.entity.MemberAgreement;
import com.hubo.gillajabi.member.domain.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Getter
@RequiredArgsConstructor
public class MailEvent {
    private final Member member;
    private final List<MemberAgreement> memberAgreements;
}