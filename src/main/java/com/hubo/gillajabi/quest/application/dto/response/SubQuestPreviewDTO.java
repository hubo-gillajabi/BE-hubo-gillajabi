package com.hubo.gillajabi.quest.application.dto.response;

import com.hubo.gillajabi.quest.infrastructure.dto.projection.MainQuestWithSubQuestProjection;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SubQuestPreviewDTO {
    private Long totalCount;
    private Long achievedCount;
    private Long unAchievedCount;

    public SubQuestPreviewDTO(final Long totalCount, final Long achievedCount) {
        this.totalCount = totalCount;
        this.achievedCount = achievedCount;
        this.unAchievedCount = totalCount - achievedCount;
    }

    public static SubQuestPreviewDTO createSubQuestPreview(MainQuestWithSubQuestProjection projection) {
        final Long totalCount = projection.getSubQuestCount();
        final Long achievedCount = projection.getAchievedSubQuestCount();

        return new SubQuestPreviewDTO(totalCount, achievedCount);
    }
}