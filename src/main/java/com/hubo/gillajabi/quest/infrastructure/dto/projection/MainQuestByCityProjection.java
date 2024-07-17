package com.hubo.gillajabi.quest.infrastructure.dto.projection;

import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.quest.domain.entity.MainQuest;

import java.time.LocalDateTime;

public interface MainQuestByCityProjection {
    MainQuest getMainQuest();
    City getCity();
    Boolean getIsAchieved();
    LocalDateTime getCreatedTime();
    Long getSubQuestCount();
    Long getAchievedSubQuestCount();
}
