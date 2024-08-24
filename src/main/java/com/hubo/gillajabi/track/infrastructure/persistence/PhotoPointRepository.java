package com.hubo.gillajabi.track.infrastructure.persistence;

import com.hubo.gillajabi.track.domain.entity.PhotoPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoPointRepository extends JpaRepository<PhotoPoint, Long> {
}
