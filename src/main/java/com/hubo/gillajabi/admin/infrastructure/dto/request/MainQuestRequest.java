package com.hubo.gillajabi.admin.infrastructure.dto.request;

import com.hubo.gillajabi.admin.application.dto.request.MainQuestCreateRequest;
import com.hubo.gillajabi.admin.application.dto.request.MainQuestUpdateRequest;
import com.hubo.gillajabi.city.domain.City;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.global.type.StatusType;
import com.hubo.gillajabi.quest.domain.entity.MainQuest;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MainQuestRequest {

    private String title;
    private String description;
    private City city;
    private CourseTheme courseTheme;
    private Course course;
    private String imageUrl;
    private StatusType status;

    private MainQuestRequest(MainQuestCreateRequest mainQuestCreateRequest, City city,
                             CourseTheme courseTheme, Course course) {
        this.title = mainQuestCreateRequest.title();
        this.description = mainQuestCreateRequest.description();
        this.city = city;
        this.courseTheme = courseTheme;
        this.course = course;
        this.imageUrl = mainQuestCreateRequest.imageUrl();
        this.status = StatusType.ENABLE;
    }

    private MainQuestRequest(MainQuestUpdateRequest mainQuestUpdateRequest, City city,
                             CourseTheme courseTheme, Course course) {
        this.title = mainQuestUpdateRequest.title();
        this.description = mainQuestUpdateRequest.description();
        this.city = city;
        this.courseTheme = courseTheme;
        this.course = course;
        this.imageUrl = mainQuestUpdateRequest.imageUrl();
        this.status = mainQuestUpdateRequest.status();
    }

    public static MainQuestRequest of(MainQuestCreateRequest mainQuestCreateRequest, City city,
                                      CourseTheme courseTheme, Course course) {
        return new MainQuestRequest(mainQuestCreateRequest, city, courseTheme, course);
    }

    public static MainQuestRequest of(MainQuest mainQuest, MainQuestUpdateRequest mainQuestUpdateRequest, City city,
                                      CourseTheme courseTheme, Course course) {
        MainQuestRequest mainQuestRequest = new MainQuestRequest(mainQuestUpdateRequest, city, courseTheme, course);
        if(mainQuestRequest.title == null){
            mainQuestRequest.title = mainQuest.getTitle();
        }
        if(mainQuestRequest.description == null){
            mainQuestRequest.description = mainQuest.getDescription();
        }
        if(mainQuestRequest.imageUrl == null){
            mainQuestRequest.imageUrl = mainQuest.getImageUrl();
        }
        if(mainQuestRequest.status == null){
            mainQuestRequest.status = mainQuest.getStatus();
        }

        return mainQuestRequest;
    }
}

