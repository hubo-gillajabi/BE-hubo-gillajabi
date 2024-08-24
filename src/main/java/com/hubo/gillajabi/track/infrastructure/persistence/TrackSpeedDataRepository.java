package com.hubo.gillajabi.track.infrastructure.persistence;

import com.hubo.gillajabi.track.domain.entity.TrackSpeedData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackSpeedDataRepository extends JpaRepository<TrackSpeedData, Long>{
}
