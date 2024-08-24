package com.hubo.gillajabi.track.infrastructure.persistence;

import com.hubo.gillajabi.track.domain.entity.TrackStatus;
import org.springframework.data.repository.CrudRepository;

public interface TrackStatusRepository extends CrudRepository<TrackStatus, String>{
    TrackStatus getEntityById(String trackStatusKeyByMember);
}

