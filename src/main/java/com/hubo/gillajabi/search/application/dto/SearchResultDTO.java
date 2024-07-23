package com.hubo.gillajabi.search.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResultDTO {
    private String id;
    private SearchCityInfoDTO city;
    private List<String> images;
    private List<SearchThemeInfoDTO> themes;
    private SearchWeatherInfoDTO weather;
    private SearchThemeInfoDTO theme;         // 테마 기반 검색 결과일 때 사용
    private List<String> tags;      // 테마 기반 검색 결과일 때 사용
}