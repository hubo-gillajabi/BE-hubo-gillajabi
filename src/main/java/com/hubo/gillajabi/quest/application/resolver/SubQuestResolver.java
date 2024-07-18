package com.hubo.gillajabi.quest.application.resolver;

import com.hubo.gillajabi.login.application.annotation.UserOnly;
import com.hubo.gillajabi.login.infrastructure.util.SecurityUtil;
import com.hubo.gillajabi.quest.application.dto.response.SubQuestPageResponse;
import com.hubo.gillajabi.quest.domain.service.SubQuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SubQuestResolver {

    private final SubQuestService subQuestService;

    @UserOnly
    @QueryMapping
    public SubQuestPageResponse subQuestsByMainQuestId(@Argument("mainQuestId") Long mainQuestId){
        String username = SecurityUtil.getCurrentUsername();

        return subQuestService.findSubQuestsByMainQuestId(mainQuestId, username);
    }
}
