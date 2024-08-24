package com.hubo.gillajabi.track.infrastructure.persistence;

import com.hubo.gillajabi.track.domain.entity.TrackGpsData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackGpsDataRepository extends JpaRepository<TrackGpsData, Long> {
}
