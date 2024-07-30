package com.hubo.gillajabi.search.domain.service;


import com.hubo.gillajabi.search.application.dto.SearchCityInfoDTO;
import com.hubo.gillajabi.search.application.dto.SearchResultDTO;
import com.hubo.gillajabi.search.application.dto.SearchThemeInfoDTO;
import com.hubo.gillajabi.search.application.dto.SearchWeatherInfoDTO;
import com.hubo.gillajabi.search.application.dto.response.SearchResponse;
import com.hubo.gillajabi.search.domain.document.CourseSearchDocument;
import com.hubo.gillajabi.search.infrastructure.persistence.CourseSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final CourseSearchRepository courseSearchRepository;

    public SearchResponse searchByKeyword(String keyword) {
        List<CourseSearchDocument> searchResults = courseSearchRepository.searchAllFields(keyword, PageRequest.of(0, 20));

        List<SearchResultDTO> results = searchResults.stream()
                .map(this::mapToSearchResultDTO)
                .collect(Collectors.toList());

        SearchResponse response = new SearchResponse();
        response.setResults(results);
        return response;
    }

    private SearchResultDTO mapToSearchResultDTO(CourseSearchDocument doc) {
        SearchResultDTO result = new SearchResultDTO();
        result.setId(doc.getId());
        result.setCity(mapToCity(doc.getCity()));
        result.setImages(doc.getImages());
        result.setWeather(mapToWeather(doc.getWeather()));

        if (doc.getThemes() != null && !doc.getThemes().isEmpty()) {
            // 도시 기반 검색 결과
            result.setThemes(doc.getThemes().stream().map(this::mapToTheme).collect(Collectors.toList()));
        } else if (doc.getTheme() != null) {
            // 테마 기반 검색 결과
            result.setTheme(mapToTheme(doc.getTheme()));
            result.setTags(doc.getTags());
        }

        return result;
    }

    private SearchCityInfoDTO mapToCity(CourseSearchDocument.City city) {
        SearchCityInfoDTO cityDTO = new SearchCityInfoDTO();
        cityDTO.setId(city.getId());
        cityDTO.setName(city.getName());
        cityDTO.setProvince(city.getProvince());
        return cityDTO;
    }

    private SearchThemeInfoDTO mapToTheme(CourseSearchDocument.Theme theme) {
        SearchThemeInfoDTO themeDTO = new SearchThemeInfoDTO();
        themeDTO.setId(theme.getId());
        themeDTO.setName(theme.getName());
        themeDTO.setShortDescription(theme.getShortDescription());
        return themeDTO;
    }

    private SearchWeatherInfoDTO mapToWeather(CourseSearchDocument.WeatherInfo weather) {
        SearchWeatherInfoDTO weatherDTO = new SearchWeatherInfoDTO();
        weatherDTO.setLowestTemperature(weather.getLowestTemperature());
        weatherDTO.setHighestTemperature(weather.getHighestTemperature());
        weatherDTO.setPrecipitationForm(weather.getPrecipitationForm());
        weatherDTO.setSkyCondition(weather.getSkyCondition());
        return weatherDTO;
    }
}