package com.hubo.gillajabi.login.infrastructure.persistence;

import com.hubo.gillajabi.login.domain.entity.MemberAuthentication;
import com.hubo.gillajabi.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberAuthenticationRepository extends JpaRepository<MemberAuthentication, Long> {

    @Query("SELECT ma FROM MemberAuthentication ma WHERE ma.member.id = :memberId")
    Optional<MemberAuthentication> findByMemberId(@Param("memberId") Long memberId);

    Optional<MemberAuthentication> findByMember(Member member);
}
