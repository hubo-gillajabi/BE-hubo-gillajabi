package com.hubo.gillajabi.track.infrastructure.persistence;

import com.hubo.gillajabi.track.domain.entity.TrackElevationData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackElevationDataRepository extends JpaRepository<TrackElevationData, Long> {
}
