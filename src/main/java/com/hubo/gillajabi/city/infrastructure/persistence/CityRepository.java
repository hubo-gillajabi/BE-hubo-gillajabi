package com.hubo.gillajabi.city.infrastructure.persistence;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.city.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByNameAndProvince(String name, Province province);

    // TODO
    default City getEntityById(final Long cityId) {
        if(cityId != null) {
            return findById(cityId).orElseThrow(() -> new IllegalArgumentException("도시를 찾을 수 없습니다."));
        }
        return null;
    }
}

