package com.hubo.gillajabi.member.infrastructure.persistence;

import com.hubo.gillajabi.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>{

}
