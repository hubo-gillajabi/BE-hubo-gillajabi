package com.hubo.gillajabi.quest.infrastructure.persistence;

import com.hubo.gillajabi.quest.domain.entity.SubQuest;
import com.hubo.gillajabi.quest.infrastructure.dto.projection.SubQuestWithStatusProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubQuestRepository extends JpaRepository<SubQuest, Long> {

    default SubQuest getEntityById(Long subQuestId){
        if(subQuestId != null){
            return findById(subQuestId).orElseThrow(() -> new IllegalArgumentException("해당 서브 퀘스트가 존재하지 않습니다."));
        }
        return null;
    }

    @Query("SELECT s AS subQuest, " +
            "CASE WHEN sqs.id IS NOT NULL THEN true ELSE false END AS isAchived, " +
            "sqs.createdTime AS createdTime " +
            "FROM SubQuest s " +
            "LEFT JOIN SubQuestStatus sqs ON s.id = sqs.subQuest.id AND sqs.member.id = :memberId " +
            "WHERE s.mainQuest.id = :mainQuestId " +
            "ORDER BY CASE WHEN sqs.id IS NOT NULL THEN 1 ELSE 0 END ASC, s.id ASC")
    List<SubQuestWithStatusProjection> findByMainQuestIdWithStatus(
            @Param("mainQuestId") Long mainQuestId,
            @Param("memberId") Long memberId
    );
}
