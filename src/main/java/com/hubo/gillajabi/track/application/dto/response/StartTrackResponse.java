package com.hubo.gillajabi.track.application.dto.response;

import com.hubo.gillajabi.track.domain.entity.TrackRecord;


public record StartTrackResponse(Long trackRecordId) {

    public static StartTrackResponse from(TrackRecord trackRecord) {
        return new StartTrackResponse(trackRecord.getId());
    }
}