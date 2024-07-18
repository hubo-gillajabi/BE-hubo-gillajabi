package com.hubo.gillajabi.quest.application.dto.response;

import com.hubo.gillajabi.global.common.dto.PageInfo;
import com.hubo.gillajabi.quest.infrastructure.dto.projection.MainQuestWithSubQuestProjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

class MainQuestPageResponseTest {

    @Test
    @DisplayName("MainQuestPageResponse.of() 메서드는 MainQuestPageResponse를 생성한다")
    public void one() {
        // given
        List<MainQuestWithSubQuestProjection> projections = Collections.singletonList(
                Mockito.mock(MainQuestWithSubQuestProjection.class)
        );

        Page<?> page = new PageImpl<>(projections, PageRequest.of(0, 10), 1);

        MockedStatic<MainQuestDTO> mockedMainQuestDTO = Mockito.mockStatic(MainQuestDTO.class);
        MockedStatic<PageInfo> mockedPageInfo = Mockito.mockStatic(PageInfo.class);

        mockedMainQuestDTO.when(() -> MainQuestDTO.listFrom(any())).thenReturn(mockedMainQuestDTO);
        mockedPageInfo.when(() -> PageInfo.from(any())).thenReturn(mockedPageInfo);

        // when
        MainQuestPageResponse result = MainQuestPageResponse.of(projections, page);

        // then
        assertNotNull(result);

    }
}
