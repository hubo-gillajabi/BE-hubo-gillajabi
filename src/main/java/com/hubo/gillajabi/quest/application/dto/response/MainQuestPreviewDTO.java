package com.hubo.gillajabi.quest.application.dto.response;

import com.hubo.gillajabi.quest.domain.entity.MainQuest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MainQuestPreviewDTO {
    private Long id;
    private String title;

    public static MainQuestPreviewDTO fromEntity(MainQuest mainQuest) {
        return new MainQuestPreviewDTO(mainQuest.getId(), mainQuest.getTitle());
    }
}