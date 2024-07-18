package com.hubo.gillajabi.quest.infrastructure.persistence;

import com.hubo.gillajabi.admin.application.dto.request.MainQuestCreateRequest;
import com.hubo.gillajabi.admin.infrastructure.dto.request.MainQuestRequest;
import com.hubo.gillajabi.crawl.application.dto.response.RoadCrawlResponse;
import com.hubo.gillajabi.quest.domain.entity.MainQuest;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MainQuestRepositoryTest {

    @Mock
    private MainQuestRepository mainQuestRepository;

    @Test
    @DisplayName("getEntityById 메서드 테스트, ID가 null일 때 null을 반환한다.")
    void one() {
        MainQuest result = mainQuestRepository.getEntityById(null);

        assertNull(result);
        verify(mainQuestRepository, never()).findById(any());
    }

}
