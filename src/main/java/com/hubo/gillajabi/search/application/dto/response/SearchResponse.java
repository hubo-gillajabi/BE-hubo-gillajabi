package com.hubo.gillajabi.search.application.dto.response;

import com.hubo.gillajabi.search.application.dto.SearchResultDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResponse {
    private List<SearchResultDTO> results;
}