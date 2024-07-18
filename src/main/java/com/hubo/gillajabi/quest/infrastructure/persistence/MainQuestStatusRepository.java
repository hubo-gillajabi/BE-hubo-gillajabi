package com.hubo.gillajabi.quest.infrastructure.persistence;

import com.hubo.gillajabi.quest.domain.entity.MainQuestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainQuestStatusRepository extends JpaRepository<MainQuestStatus, Long> {
}