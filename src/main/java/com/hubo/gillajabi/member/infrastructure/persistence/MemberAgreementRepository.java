package com.hubo.gillajabi.member.infrastructure.persistence;

import com.hubo.gillajabi.member.domain.entity.MemberAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAgreementRepository extends JpaRepository<MemberAgreement, Long> {
}
