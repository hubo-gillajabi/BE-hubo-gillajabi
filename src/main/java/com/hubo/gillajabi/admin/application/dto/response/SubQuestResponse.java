package com.hubo.gillajabi.admin.application.dto.response;

import com.hubo.gillajabi.quest.application.dto.response.MainQuestPreviewDTO;
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

    private MainQuestPreviewDTO mainQuest;

    private StatusType statusType;

    public static SubQuestResponse fromEntity(SubQuest subQuest) {
        MainQuestPreviewDTO mainQuestPreviewDTO = MainQuestPreviewDTO.fromEntity(subQuest.getMainQuest());

        return new SubQuestResponse(
                subQuest.getId(),
                subQuest.getTitle(),
                subQuest.getDescription(),
                subQuest.getImageUrl(),
                mainQuestPreviewDTO,
                subQuest.getStatus()
        );
    }

}
