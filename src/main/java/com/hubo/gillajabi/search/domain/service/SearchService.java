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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final CourseSearchRepository courseSearchRepository;

    public SearchResponse searchByCity(String keyword) {
        List<CourseSearchDocument> searchResults = courseSearchRepository.searchByCity(keyword, PageRequest.of(0, 20));
        return mapToSearchResponse(searchResults, false);
    }

    public SearchResponse searchByTheme(String keyword) {
        List<CourseSearchDocument> searchResults = courseSearchRepository.searchByTheme(keyword, PageRequest.of(0, 20));
        return mapToSearchResponse(searchResults, true);
    }

    private SearchResponse mapToSearchResponse(List<CourseSearchDocument> documents, boolean isThemeSearch) {
        List<SearchResultDTO> results = documents.stream()
                .filter(doc -> isThemeSearch ? doc.getId().startsWith("theme_") : doc.getId().startsWith("city_"))
                .map(doc -> mapToSearchResultDTO(doc, isThemeSearch))
                .collect(Collectors.toList());

        SearchResponse response = new SearchResponse();
        response.setResults(results);
        return response;
    }

    private SearchResultDTO mapToSearchResultDTO(CourseSearchDocument doc, boolean isThemeSearch) {
        SearchResultDTO result = new SearchResultDTO();
        result.setId(doc.getId());
        result.setCity(mapToCity(doc.getCity()));
        result.setImages(doc.getImages() != null ? doc.getImages() : new ArrayList<>());
        result.setWeather(mapToWeather(doc.getWeather()));

        if (isThemeSearch) {
            result.setTheme(mapToTheme(doc.getTheme()));
            result.setTags(doc.getTags() != null ? doc.getTags() : new ArrayList<>());
        } else {
            result.setThemes(doc.getThemes() != null ? doc.getThemes().stream().map(this::mapToTheme).collect(Collectors.toList()) : new ArrayList<>());
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