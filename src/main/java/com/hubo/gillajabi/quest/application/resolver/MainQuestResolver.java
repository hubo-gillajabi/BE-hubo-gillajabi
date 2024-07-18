package com.hubo.gillajabi.quest.application.resolver;

import com.hubo.gillajabi.login.application.annotation.UserOnly;
import com.hubo.gillajabi.login.infrastructure.util.SecurityUtil;
import com.hubo.gillajabi.quest.application.dto.response.MainQuestPageResponse;
import com.hubo.gillajabi.quest.domain.service.MainQuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MainQuestResolver {

    private final MainQuestService mainQuestService;

    @UserOnly
    @QueryMapping
    public MainQuestPageResponse mainQuestsByCity(@Argument("page") @DefaultValue("0") Integer page,
                                                  @Argument("size") @DefaultValue("20") Integer size) {
        String username = SecurityUtil.getCurrentUsername();
        Pageable pageable = PageRequest.of(page, size);

        return mainQuestService.findMainQuestsByCity(pageable, username);
    }


    @UserOnly
    @QueryMapping
    public MainQuestPageResponse mainQuestsByTheme(@Argument("page") @DefaultValue("0") final Integer page,
                                                   @Argument("size") @DefaultValue("20")final Integer size) {
        final String username = SecurityUtil.getCurrentUsername();
        Pageable pageable = PageRequest.of(page, size);

        return mainQuestService.findMainQuestsByTheme(pageable, username);
    }

    @UserOnly
    @QueryMapping
    public MainQuestPageResponse mainQuestsByCourse(@Argument("page") @DefaultValue("0") final Integer page,
                                                    @Argument("size") @DefaultValue("20") final Integer size){
        final String username = SecurityUtil.getCurrentUsername();
        Pageable pageable = PageRequest.of(page, size);

        return mainQuestService.findMainQuestsByCourse(pageable, username);
    }

    //TODO: 추가 , 축제 등등
}
