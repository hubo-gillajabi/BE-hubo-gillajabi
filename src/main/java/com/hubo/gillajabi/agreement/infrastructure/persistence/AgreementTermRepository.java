package com.hubo.gillajabi.agreement.infrastructure.persistence;

import com.hubo.gillajabi.agreement.domain.entity.AgreementTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementTermRepository extends JpaRepository<AgreementTerm, Long> {
}
