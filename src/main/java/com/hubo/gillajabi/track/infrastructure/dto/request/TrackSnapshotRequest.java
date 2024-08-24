package com.hubo.gillajabi.track.infrastructure.dto.request;

import com.hubo.gillajabi.track.application.dto.request.TrackSendRequest;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackSnapshotRequest {
    private Long trackRecordId;
    private List<List<String>> gpsData;
    private List<Integer> elevationData;
    private List<Integer> speedData;

    public static TrackSnapshotRequest of(final TrackSendRequest request, final Long trackId) {
        TrackSnapshotRequest trackSnapshotRequest = new TrackSnapshotRequest();
        trackSnapshotRequest.setTrackRecordId(trackId);
        trackSnapshotRequest.setGpsData(request.getGpsData());
        trackSnapshotRequest.setElevationData(request.getElevationData());
        trackSnapshotRequest.setSpeedData(request.getSpeedData());
        return trackSnapshotRequest;
    }
}


