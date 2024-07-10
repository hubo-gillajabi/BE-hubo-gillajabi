package com.hubo.gillajabi.admin.application.dto;

import com.hubo.gillajabi.quest.domain.entity.MainQuest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MainQuestDTO {
    private Long id;
    private String title;

    public static MainQuestDTO fromEntity(MainQuest mainQuest) {
        return new MainQuestDTO(mainQuest.getId(), mainQuest.getTitle());
    }
}