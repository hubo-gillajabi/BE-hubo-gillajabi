package com.hubo.gillajabi.quest.infrastructure.dto.projection;

import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.quest.domain.entity.MainQuest;

import java.time.LocalDateTime;

public interface MainQuestWithSubQuestProjection {
    MainQuest getMainQuest();
    City getCity();
    Course getCourse();
    CourseTheme getCourseTheme();
    Boolean getIsAchieved();
    LocalDateTime getCreatedTime();
    Long getSubQuestCount();
    Long getAchievedSubQuestCount();
}
