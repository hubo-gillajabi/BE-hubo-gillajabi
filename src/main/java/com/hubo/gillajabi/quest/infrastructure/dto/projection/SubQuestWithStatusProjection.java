package com.hubo.gillajabi.quest.infrastructure.dto.projection;

import com.hubo.gillajabi.quest.domain.entity.SubQuest;
import com.hubo.gillajabi.quest.domain.entity.SubQuestStatus;

import java.time.LocalDateTime;

public interface SubQuestWithStatusProjection {
    SubQuest getSubQuest();
    Boolean getIsAchived();
    LocalDateTime getCreatedTime();
}
