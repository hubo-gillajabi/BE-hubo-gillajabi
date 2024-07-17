package com.hubo.gillajabi.quest.application.dto.response;

import com.hubo.gillajabi.quest.infrastructure.dto.projection.MainQuestByCityProjection;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SubQuestPreviewDTO {
    private Long totalCount;
    private Long achievedCount;
    private Long unAchievedCount;

    public SubQuestPreviewDTO(Long totalCount, Long achievedCount) {
        this.totalCount = totalCount;
        this.achievedCount = achievedCount;
        this.unAchievedCount = totalCount - achievedCount;
    }

    public static SubQuestPreviewDTO createSubQuestPreview(MainQuestByCityProjection projection) {
        Long totalCount = projection.getSubQuestCount();
        Long achievedCount = projection.getAchievedSubQuestCount();

        return new SubQuestPreviewDTO(totalCount, achievedCount);
    }
}