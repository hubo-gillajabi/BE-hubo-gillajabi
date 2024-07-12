package com.hubo.gillajabi.city.domain.service;

import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.city.infrastructure.persistence.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<City> getAllCities(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return cityRepository.findAll(pageable).getContent();
    }

    public City getCityDetails(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(() -> new RuntimeException("City not found"));
    }
}