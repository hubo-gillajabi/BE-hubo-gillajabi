package com.hubo.gillajabi.track.infrastructure.persistence;

import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.track.domain.entity.TrackRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRecordRepository extends JpaRepository<TrackRecord, Long> {


    default TrackRecord getEntityById(final Long trackId) {
        if(trackId != null) {
            return findById(trackId).orElseThrow(() -> new IllegalArgumentException("트랙을 찾을 수 없습니다."));
        }
        return null;
    }
}
