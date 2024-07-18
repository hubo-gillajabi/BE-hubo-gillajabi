package com.hubo.gillajabi.member.infrastructure.persistence;

import com.hubo.gillajabi.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{

    Optional<Member> findByNickName(final String nickName);


    default Member getEntityByUserName(final String userName){
        if(userName != null){
            return findByNickName(userName).orElseThrow(() -> new IllegalArgumentException("Member를 찾을 수 없습니다."));
        }
        return null;
    }

}
