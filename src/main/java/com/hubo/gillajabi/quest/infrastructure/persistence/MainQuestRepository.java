package com.hubo.gillajabi.quest.infrastructure.persistence;

import com.hubo.gillajabi.quest.domain.entity.MainQuest;
import com.hubo.gillajabi.quest.infrastructure.dto.projection.MainQuestWithSubQuestProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MainQuestRepository extends JpaRepository<MainQuest, Long> {


    default MainQuest getEntityById(final Long mainQuestId) {
        if (mainQuestId != null) {
            return findById(mainQuestId).orElseThrow(() -> new IllegalArgumentException("메인 퀘스트를 찾을 수 없습니다."));
        }
        return null;
    }

    @Query("SELECT mq.id FROM MainQuest mq " +
            "JOIN mq.city c " +
            "LEFT JOIN MainQuestStatus mqStatus ON mqStatus.mainQuest = mq AND mqStatus.member.id = :memberId " +
            "WHERE mq.course IS NULL AND mq.courseTheme IS NULL " +
            "ORDER BY CASE WHEN mqStatus.id IS NOT NULL THEN 0 ELSE 1 END, " +
            "mqStatus.id DESC NULLS LAST, mq.id ASC")
    Page<Long> findMainQuestIdsWithCityByMember(@Param("memberId") final Long memberId, final Pageable pageable);


    @Query("SELECT mq.id FROM MainQuest mq " +
            "JOIN mq.courseTheme ct " +
            "LEFT JOIN MainQuestStatus mqStatus ON mqStatus.mainQuest = mq AND mqStatus.member.id = :memberId " +
            "WHERE mq.city IS NULL AND mq.course IS NULL " +
            "ORDER BY CASE WHEN mqStatus.id IS NOT NULL THEN 0 ELSE 1 END, " +
            "mqStatus.id DESC NULLS LAST, mq.id ASC")
    Page<Long> findMainQuestIdsWithCourseThemeByMember(@Param("memberId") final Long memberId, final Pageable pageable);


    @Query("SELECT mq.id FROM MainQuest mq " +
            "JOIN mq.course c " +
            "LEFT JOIN MainQuestStatus mqStatus ON mqStatus.mainQuest = mq AND mqStatus.member.id = :memberId " +
            "WHERE mq.city IS NULL AND mq.courseTheme IS NULL " +
            "ORDER BY CASE WHEN mqStatus.id IS NOT NULL THEN 0 ELSE 1 END, " +
            "mqStatus.id DESC NULLS LAST, mq.id ASC")
    Page<Long> findMainQuestIdsWithCourseByMember(@Param("memberId") final Long memberId, final Pageable pageable);

    @Query("SELECT mq as mainQuest, c as city, " +
            "CASE WHEN mqStatus.id IS NOT NULL THEN true ELSE false END as isAchieved, " +
            "mqStatus.createdTime as createdTime, " +
            "(SELECT COUNT(sq) FROM SubQuest sq WHERE sq.mainQuest = mq) as subQuestCount, " +
            "(SELECT COUNT(sqs) FROM SubQuestStatus sqs WHERE sqs.subQuest IN (SELECT sq FROM SubQuest sq WHERE sq.mainQuest = mq) AND sqs.member.id = :memberId) as achievedSubQuestCount " +
            "FROM MainQuest mq " +
            "JOIN mq.city c " +
            "LEFT JOIN MainQuestStatus mqStatus ON mqStatus.mainQuest = mq AND mqStatus.member.id = :memberId " +
            "WHERE mq.id IN :ids " +
            "AND mq.course IS NULL AND mq.courseTheme IS NULL " +
            "ORDER BY CASE WHEN mqStatus.id IS NOT NULL THEN 0 ELSE 1 END, " +
            "mqStatus.id DESC NULLS LAST, mq.id ASC")
    List<MainQuestWithSubQuestProjection> findMainQuestsByIdsWithCityAndMember(@Param("ids") List<Long> ids, @Param("memberId") final Long memberId);

    @Query("SELECT mq as mainQuest, ct as courseTheme, " +
            "CASE WHEN mqStatus.id IS NOT NULL THEN true ELSE false END as isAchieved, " +
            "mqStatus.createdTime as createdTime, " +
            "(SELECT COUNT(sq) FROM SubQuest sq WHERE sq.mainQuest = mq) as subQuestCount, " +
            "(SELECT COUNT(sqs) FROM SubQuestStatus sqs WHERE sqs.subQuest IN (SELECT sq FROM SubQuest sq WHERE sq.mainQuest = mq) AND sqs.member.id = :memberId) as achievedSubQuestCount " +
            "FROM MainQuest mq " +
            "JOIN mq.courseTheme ct " +
            "LEFT JOIN MainQuestStatus mqStatus ON mqStatus.mainQuest = mq AND mqStatus.member.id = :memberId " +
            "WHERE mq.id IN :ids " +
            "AND mq.city IS NULL AND mq.course IS NULL " +
            "ORDER BY CASE WHEN mqStatus.id IS NOT NULL THEN 0 ELSE 1 END, " +
            "mqStatus.id DESC NULLS LAST, mq.id ASC")
    List<MainQuestWithSubQuestProjection> findMainQuestsByIdsWithCourseThemeAndMember(@Param("ids") List<Long> ids, @Param("memberId") final Long memberId);


    @Query("SELECT mq as mainQuest, c as course, " +
            "CASE WHEN mqStatus.id IS NOT NULL THEN true ELSE false END as isAchieved, " +
            "mqStatus.createdTime as createdTime, " +
            "(SELECT COUNT(sq) FROM SubQuest sq WHERE sq.mainQuest = mq) as subQuestCount, " +
            "(SELECT COUNT(sqs) FROM SubQuestStatus sqs WHERE sqs.subQuest IN (SELECT sq FROM SubQuest sq WHERE sq.mainQuest = mq) AND sqs.member.id = :memberId) as achievedSubQuestCount " +
            "FROM MainQuest mq " +
            "JOIN mq.course c " +
            "LEFT JOIN MainQuestStatus mqStatus ON mqStatus.mainQuest = mq AND mqStatus.member.id = :memberId " +
            "WHERE mq.id IN :ids " +
            "AND mq.city IS NULL AND mq.courseTheme IS NULL " +
            "ORDER BY CASE WHEN mqStatus.id IS NOT NULL THEN 0 ELSE 1 END, " +
            "mqStatus.id DESC NULLS LAST, mq.id ASC")
    List<MainQuestWithSubQuestProjection> findMainQuestsByIdsWithCourseAndMember(@Param("ids") List<Long> ids, @Param("memberId") final Long memberId);
}
