package com.hubo.gillajabi.admin.application.dto.response;

import com.hubo.gillajabi.admin.application.dto.MainQuestDTO;
import com.hubo.gillajabi.global.type.StatusType;
import com.hubo.gillajabi.quest.domain.entity.SubQuest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SubQuestResponse {

    private Long id;

    private String title;

    private String description;

    private String imageUrl;

    private MainQuestDTO mainQuest;

    private StatusType statusType;

    public static SubQuestResponse fromEntity(SubQuest subQuest) {
        MainQuestDTO mainQuestDTO = MainQuestDTO.fromEntity(subQuest.getMainQuest());

        return new SubQuestResponse(
                subQuest.getId(),
                subQuest.getTitle(),
                subQuest.getDescription(),
                subQuest.getImageUrl(),
                mainQuestDTO,
                subQuest.getStatus()
        );
    }

}
