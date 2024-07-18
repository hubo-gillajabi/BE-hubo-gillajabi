package com.hubo.gillajabi.quest.domain.entity;

import com.hubo.gillajabi.admin.infrastructure.dto.request.MainQuestRequest;
import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.global.BaseEntity;
import com.hubo.gillajabi.global.type.StatusType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
@DynamicUpdate
@Builder
public class MainQuest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 400)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_theme_id")
    private CourseTheme courseTheme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column
    private String imageUrl;

    @OneToMany(mappedBy = "mainQuest", fetch = FetchType.LAZY)
    private Set<SubQuest> subQuests;

    public static MainQuest createMainQuest(final MainQuestRequest mainQuestRequest) {
        return new MainQuest(null, mainQuestRequest.getTitle(), mainQuestRequest.getDescription(), mainQuestRequest.getCity(), mainQuestRequest.getCourseTheme(), mainQuestRequest.getCourse(), mainQuestRequest.getImageUrl(), null);
    }

    @Override
    public void changeStatusToDeleted() {
        super.changeStatusToDeleted();
        this.subQuests.forEach(SubQuest::changeStatusToDeleted);
    }

    public void removeSubQuest(final SubQuest subQuest) {
        this.subQuests.remove(subQuest);
    }

    public boolean hasSubQuest(final SubQuest subQuest) {
        return subQuests != null && subQuests.contains(subQuest);
    }

    public void update(final MainQuestRequest mainQuestRequest) {
        this.title = mainQuestRequest.getTitle();
        this.description = mainQuestRequest.getDescription();
        this.city = mainQuestRequest.getCity();
        this.courseTheme = mainQuestRequest.getCourseTheme();
        this.course = mainQuestRequest.getCourse();
        this.imageUrl = mainQuestRequest.getImageUrl();
        this.changeStatusToUpdate(mainQuestRequest.getStatus());
    }

    private void changeStatusToUpdate(final StatusType statusType){
        if(statusType.equals(StatusType.DISABLE)){
            this.changeStatusToDisable();
            this.subQuests.forEach(SubQuest::changeStatusToDisable);
        }
        if(statusType.equals(StatusType.ENABLE)){
            this.changeStatusToEnable();
            this.subQuests.forEach(SubQuest::changeStatusToEnable);
        }
    }
}
