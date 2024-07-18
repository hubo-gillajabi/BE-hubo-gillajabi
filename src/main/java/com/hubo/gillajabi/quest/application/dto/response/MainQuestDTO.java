package com.hubo.gillajabi.quest.application.dto.response;

import com.hubo.gillajabi.city.application.dto.response.CityPreviewDTO;
import com.hubo.gillajabi.quest.domain.entity.MainQuest;
import com.hubo.gillajabi.quest.infrastructure.dto.projection.MainQuestWithSubQuestProjection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MainQuestDTO {
    private Long id;
    private String title;
    private String description;
    private CityPreviewDTO city;
    private CourseThemePreviewDTO courseTheme;
    private CoursePreviewDTO course;
    private String imageUrl;
    private SubQuestPreviewDTO subQuestPreview;
    private boolean isAchieved;
    private LocalDateTime achievedTime;

    private static MainQuestDTO from(MainQuestWithSubQuestProjection projection) {
        MainQuest mainQuest = projection.getMainQuest();
        return new MainQuestDTO(
                mainQuest.getId(),
                mainQuest.getTitle(),
                mainQuest.getDescription(),
                CityPreviewDTO.toEntity(mainQuest.getCity()),
                CourseThemePreviewDTO.toEntity(projection.getCourseTheme()),
                CoursePreviewDTO.toEntity(projection.getCourse()),
                mainQuest.getImageUrl(),
                SubQuestPreviewDTO.createSubQuestPreview(projection),
                projection.getIsAchieved(),
                projection.getCreatedTime()
        );
    }

    public static List<MainQuestDTO> listFrom(List<MainQuestWithSubQuestProjection> projections) {
        return projections.stream()
                .map(MainQuestDTO::from)
                .toList();
    }
}