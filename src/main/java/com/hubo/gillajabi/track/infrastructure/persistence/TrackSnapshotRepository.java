package com.hubo.gillajabi.track.infrastructure.persistence;

import com.hubo.gillajabi.track.domain.entity.TrackSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TrackSnapshotRepository extends JpaRepository<TrackSnapshot, Long> {

    List<TrackSnapshot> findByTrackRecordIdOrderByCreatedTimeAsc(Long trackId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TrackSnapshot ts WHERE ts.trackRecordId = :trackRecordId")
    void deleteByTrackRecordId(Long trackRecordId);
}
