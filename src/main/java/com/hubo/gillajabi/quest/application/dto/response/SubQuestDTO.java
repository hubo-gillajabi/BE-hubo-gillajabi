package com.hubo.gillajabi.quest.application.dto.response;


import com.hubo.gillajabi.quest.infrastructure.dto.projection.SubQuestWithStatusProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SubQuestDTO {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private Boolean isAchieved;
    private LocalDateTime achievedTime;

    public static SubQuestDTO from(SubQuestWithStatusProjection projection) {
        SubQuestDTO dto = new SubQuestDTO();
        dto.setId(projection.getSubQuest().getId());
        dto.setTitle(projection.getSubQuest().getTitle());
        dto.setDescription(projection.getSubQuest().getDescription());
        dto.setImageUrl(projection.getSubQuest().getImageUrl());
        dto.setIsAchieved(projection.getIsAchived());
        dto.setAchievedTime(projection.getIsAchived() ? projection.getCreatedTime() : null);
        return dto;
    }
}

