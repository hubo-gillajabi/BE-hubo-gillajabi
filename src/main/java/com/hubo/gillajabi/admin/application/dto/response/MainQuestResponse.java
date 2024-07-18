package com.hubo.gillajabi.admin.application.dto.response;

import com.hubo.gillajabi.admin.application.dto.CityResponseDTO;
import com.hubo.gillajabi.admin.application.dto.CourseResponseDTO;
import com.hubo.gillajabi.admin.application.dto.CourseThemeResponseDTO;
import com.hubo.gillajabi.global.type.StatusType;
import com.hubo.gillajabi.quest.domain.entity.MainQuest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MainQuestResponse {
    private Long id;
    private String title;
    private String description;
    private CityResponseDTO city;
    private CourseThemeResponseDTO courseTheme;
    private CourseResponseDTO course;
    private String imageUrl;
    private StatusType statusType;

    public static MainQuestResponse fromEntity(MainQuest mainQuest) {
        CityResponseDTO cityResponseDTO = (mainQuest.getCity() != null) ? CityResponseDTO.fromEntity(mainQuest.getCity()) : null;
        CourseThemeResponseDTO courseThemeResponseDTO = (mainQuest.getCourseTheme() != null) ? CourseThemeResponseDTO.fromEntity(mainQuest.getCourseTheme()) : null;
        CourseResponseDTO courseResponseDTO = (mainQuest.getCourse() != null) ? CourseResponseDTO.fromEntity(mainQuest.getCourse()) : null;

        return new MainQuestResponse(
                mainQuest.getId(),
                mainQuest.getTitle(),
                mainQuest.getDescription(),
                cityResponseDTO,
                courseThemeResponseDTO,
                courseResponseDTO,
                mainQuest.getImageUrl(),
                mainQuest.getStatus());
    }
}