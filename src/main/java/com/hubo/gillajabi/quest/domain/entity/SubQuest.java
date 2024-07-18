package com.hubo.gillajabi.quest.domain.entity;

import com.hubo.gillajabi.admin.infrastructure.dto.request.SubQuestRequest;
import com.hubo.gillajabi.global.BaseEntity;
import com.hubo.gillajabi.global.type.StatusType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
@DynamicUpdate
public class SubQuest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 400)
    private String description;

    @Column
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_quest_id")
    private MainQuest mainQuest;

    @OneToMany(mappedBy = "subQuest", fetch = FetchType.LAZY)
    private Set<SubQuestStatus> subQuestStatuses;


    public static SubQuest createSubQuest(SubQuestRequest subQuestRequest) {
        return new SubQuest(null, subQuestRequest.getTitle(), subQuestRequest.getDescription(), subQuestRequest.getImageUrl(), subQuestRequest.getMainQuest(),null);
    }

    public void update(SubQuestRequest subQuestRequest) {
        this.title = subQuestRequest.getTitle();
        this.description = subQuestRequest.getDescription();
        this.imageUrl = subQuestRequest.getImageUrl();
        updateMainQuest(subQuestRequest.getMainQuest());
        changeStatusToUpdate(subQuestRequest.getStatusType());
    }

    private void updateMainQuest(final MainQuest mainQuest) {
        this.mainQuest.getSubQuests().remove(this);
        this.mainQuest = mainQuest;
    }

    private void changeStatusToUpdate(final StatusType statusType){
        if(statusType.equals(StatusType.DISABLE)){
            this.changeStatusToDisable();
        }
        if(statusType.equals(StatusType.ENABLE)){
            this.changeStatusToEnable();
        }
    }
}
