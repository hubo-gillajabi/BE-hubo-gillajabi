package com.hubo.gillajabi.member.infrastructure.persistence;

import com.hubo.gillajabi.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{

    Optional<Member> findByNickName(String nickName);

}
