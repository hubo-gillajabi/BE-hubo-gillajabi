package com.hubo.gillajabi.search.application.resolver;

import com.hubo.gillajabi.search.application.dto.response.SearchResponse;
import com.hubo.gillajabi.search.domain.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import graphql.schema.DataFetchingEnvironment;

@Controller
@RequiredArgsConstructor
public class SearchResolver {

    private final SearchService searchService;

    @QueryMapping
    public SearchResponse searchCourseByKeyword(@Argument String keyword, DataFetchingEnvironment env) {
        boolean isThemeSearch = env.getSelectionSet().getFields().stream()
                .anyMatch(field -> field.getName().equals("theme"));

        if (isThemeSearch) {
            return searchService.searchByTheme(keyword);
        } else {
            return searchService.searchByCity(keyword);
        }
    }

}