package com.hubo.gillajabi.quest.application.dto.response;

import com.hubo.gillajabi.global.common.dto.PageInfo;
import com.hubo.gillajabi.quest.infrastructure.dto.projection.MainQuestByCityProjection;
import com.hubo.gillajabi.quest.infrastructure.dto.projection.MainQuestProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MainQuestPageResponse {
    private List<MainQuestDTO> content;
    private PageInfo pageInfo;

    public static MainQuestPageResponse of(List<MainQuestByCityProjection> projections, Page<?> page) {
        List<MainQuestDTO> content = MainQuestDTO.listFrom(projections);
        PageInfo pageInfo = PageInfo.from(page);
        return new MainQuestPageResponse(content, pageInfo);
    }
}
