package com.hubo.gillajabi.quest.application.dto.response;

import com.hubo.gillajabi.city.application.dto.response.CityPreviewDTO;
import com.hubo.gillajabi.quest.domain.entity.MainQuest;
import com.hubo.gillajabi.quest.infrastructure.dto.projection.MainQuestWithSubQuestProjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import org.mockito.MockedStatic;
import org.mockito.Mockito;


class MainQuestDTOTest {

    private MainQuestWithSubQuestProjection createProjection() {
        MainQuestWithSubQuestProjection projection = mock(MainQuestWithSubQuestProjection.class);
        MainQuest mainQuest = new MainQuest(1L, "제목", "내용", null, null, null, null, null);

        when(projection.getMainQuest()).thenReturn(mainQuest);
        when(projection.getIsAchieved()).thenReturn(true);
        when(projection.getCreatedTime()).thenReturn(LocalDateTime.of(2024, 7, 18, 6, 14));

        return projection;
    }

    @Test
    @DisplayName("listFrom 메서드는 MainQuestWithSubQuestProjection 목록을 MainQuestDTO 목록으로 변환한다")
    void one() {
        // given
        MainQuestWithSubQuestProjection projection = createProjection();

        // when
        List<MainQuestWithSubQuestProjection> projections = List.of(projection);
        List<MainQuestDTO> results = MainQuestDTO.listFrom(projections);

        // then
        assertEquals(1, results.size());
        assertEquals(projection.getMainQuest().getTitle(), results.get(0).getTitle());
    }
}

