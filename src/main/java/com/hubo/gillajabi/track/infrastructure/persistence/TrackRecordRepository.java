package com.hubo.gillajabi.track.infrastructure.persistence;

import com.hubo.gillajabi.track.domain.entity.TrackRecord;
import com.hubo.gillajabi.track.infrastructure.exception.TrackException;
import com.hubo.gillajabi.track.infrastructure.exception.TrackExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRecordRepository extends JpaRepository<TrackRecord, Long> {

    default TrackRecord getEntityById(final Long trackId) {
        if(trackId != null) {
            return findById(trackId).orElseThrow(() -> new TrackException(TrackExceptionCode.TRACK_NOT_FOUND));
        }
        return null;
    }
}
