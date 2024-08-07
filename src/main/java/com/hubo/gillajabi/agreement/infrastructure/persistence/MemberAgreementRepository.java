package com.hubo.gillajabi.agreement.infrastructure.persistence;

import com.hubo.gillajabi.agreement.domain.entity.MemberAgreement;
import com.hubo.gillajabi.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberAgreementRepository extends JpaRepository<MemberAgreement, Long> {

    @Query("SELECT ma FROM MemberAgreement ma RIGHT JOIN FETCH ma.agreementTerm WHERE ma.member = :member")
    List<MemberAgreement> findByMemberWithAgreementTerms(@Param("member") Member member);
}
