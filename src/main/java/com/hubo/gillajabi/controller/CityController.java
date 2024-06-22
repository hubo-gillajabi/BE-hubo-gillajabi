package com.hubo.gillajabi.controller;

import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

//TODO: graphql 테스트 용 이므로 추후에 제거할것
@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityRepository cityRepository;

    @QueryMapping
    public List<City> cities() {
        return cityRepository.findAll();
    }

    @QueryMapping
    public Optional<City> city(Long id) {
        return cityRepository.findById(id);
    }
}


