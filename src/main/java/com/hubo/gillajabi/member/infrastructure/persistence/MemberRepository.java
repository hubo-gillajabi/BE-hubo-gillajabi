package com.hubo.gillajabi.member.infrastructure.persistence;

import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.exception.MemberException;
import com.hubo.gillajabi.member.infrastructure.exception.MemberExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{

    Optional<Member> findByNickName(final String nickName);


    default Member getEntityByUserName(final String userName){
        if(userName != null){
            return findByNickName(userName).orElseThrow(() -> new MemberException(MemberExceptionCode.MEMBER_NOT_FOUND));
        }
        return null;
    }


    Optional<Member> findByEmail(String email);
}
