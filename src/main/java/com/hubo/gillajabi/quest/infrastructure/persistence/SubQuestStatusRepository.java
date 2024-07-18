package com.hubo.gillajabi.quest.infrastructure.persistence;

import com.hubo.gillajabi.quest.domain.entity.SubQuestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubQuestStatusRepository extends JpaRepository<SubQuestStatus, Long> {
}
