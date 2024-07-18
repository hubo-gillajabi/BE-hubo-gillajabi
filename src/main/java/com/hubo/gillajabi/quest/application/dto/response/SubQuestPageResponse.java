package com.hubo.gillajabi.quest.application.dto.response;

import com.hubo.gillajabi.quest.domain.entity.MainQuest;
import com.hubo.gillajabi.quest.domain.entity.SubQuest;
import com.hubo.gillajabi.quest.infrastructure.dto.projection.SubQuestWithStatusProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SubQuestPageResponse {
    Long mainQuestId;
    List<SubQuestDTO> subQuests;

    public static SubQuestPageResponse of(final Long mainQuestId, List<SubQuestWithStatusProjection> subQuestsProjections) {
        final SubQuestPageResponse response = new SubQuestPageResponse();
        response.setMainQuestId(mainQuestId);
        response.setSubQuests(subQuestsProjections.stream()
                .map(SubQuestDTO::from)
                .collect(Collectors.toList()));
        return response;
    }
}
