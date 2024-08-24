package com.hubo.gillajabi.location.infrastructure.persistence;


import com.hubo.gillajabi.location.domain.service.entity.LocationHandlingLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationHandlingLogRepository extends JpaRepository<LocationHandlingLog, Long>{

    default LocationHandlingLog getEntityById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 id가 없습니다: " + id));
    }
}
