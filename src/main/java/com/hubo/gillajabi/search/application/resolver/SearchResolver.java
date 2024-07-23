package com.hubo.gillajabi.search.application.resolver;

import com.hubo.gillajabi.search.application.dto.response.SearchResponse;
import com.hubo.gillajabi.search.domain.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SearchResolver {

    private final SearchService searchService;

    @QueryMapping
    public SearchResponse searchCourseByKeyword(@Argument String keyword) {
        return searchService.searchByKeyword(keyword);
    }
}