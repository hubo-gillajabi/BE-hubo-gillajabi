package com.hubo.gillajabi.admin.infrastructure.dto.request;

import com.hubo.gillajabi.admin.application.dto.request.SubQuestCreateRequest;
import com.hubo.gillajabi.admin.application.dto.request.SubQuestUpdateRequest;
import com.hubo.gillajabi.global.type.StatusType;
import com.hubo.gillajabi.quest.domain.entity.MainQuest;
import com.hubo.gillajabi.quest.domain.entity.SubQuest;
import lombok.Getter;

@Getter
public class SubQuestRequest {

    private String title;
    private String description;
    private String imageUrl;
    private MainQuest mainQuest;
    private StatusType statusType;

    private SubQuestRequest(SubQuestCreateRequest subQuestCreateRequest, MainQuest mainQuest) {
        this.title = subQuestCreateRequest.title();
        this.description = subQuestCreateRequest.description();
        this.imageUrl = subQuestCreateRequest.imageUrl();
        this.mainQuest = mainQuest;
        this.statusType = StatusType.ENABLE;
    }

    private SubQuestRequest(SubQuestUpdateRequest subQuestUpdateRequest, MainQuest mainQuest) {
        this.title = subQuestUpdateRequest.title();
        this.description = subQuestUpdateRequest.description();
        this.imageUrl = subQuestUpdateRequest.imageUrl();
        this.mainQuest = mainQuest;
        this.statusType = subQuestUpdateRequest.status();
    }

    public static SubQuestRequest from(SubQuestCreateRequest subQuestCreateRequest, MainQuest mainQuest) {
        return new SubQuestRequest(subQuestCreateRequest, mainQuest);
    }

    public static SubQuestRequest of(SubQuest subQuest, SubQuestUpdateRequest subQuestUpdateRequest, MainQuest mainQuest) {
        SubQuestRequest subQuestRequest = new SubQuestRequest(subQuestUpdateRequest, mainQuest);
        if (subQuestRequest.title == null) {
            subQuestRequest.title = subQuest.getTitle();
        }
        if (subQuestRequest.description == null) {
            subQuestRequest.description = subQuest.getDescription();
        }
        if (subQuestRequest.imageUrl == null) {
            subQuestRequest.imageUrl = subQuest.getImageUrl();
        }
        if (subQuestRequest.mainQuest == null) {
            subQuestRequest.mainQuest = subQuest.getMainQuest();
        }
        if(subQuestRequest.statusType == null){
            subQuestRequest.statusType = subQuest.getStatus();
        }

        return subQuestRequest;
    }
}
