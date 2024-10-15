package com.hubo.gillajabi.admin.domain.service;

import com.hubo.gillajabi.admin.application.dto.request.CityImageRequest;
import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.city.domain.entity.CityImage;
import com.hubo.gillajabi.city.infrastructure.persistence.CityImageRepository;
import com.hubo.gillajabi.city.infrastructure.persistence.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCityService {

    private final CityRepository cityRepository;

    private final CityImageRepository cityImageRepository;

    public void addCityImages(CityImageRequest request){
        City city = cityRepository.findById(request.cityId())
                .orElseThrow(() -> new IllegalArgumentException("해당 도시가 존재하지 않습니다."));
        city.addImages(request.getImageUrls());

        cityRepository.save(city);
    }

    public void deleteCityImage(Long id) {
        CityImage cityImage = cityImageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 이미지가 존재하지 않습니다."));

        cityImage.removeCity();
        cityImageRepository.delete(cityImage);
    }
}
