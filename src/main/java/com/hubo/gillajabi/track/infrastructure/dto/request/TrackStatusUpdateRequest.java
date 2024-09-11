package com.hubo.gillajabi.track.infrastructure.dto.request;

import com.hubo.gillajabi.track.application.dto.request.TrackSendRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackStatusUpdateRequest {

    private Long step;

    private BigDecimal distance;

    private Long calorie;

    public static TrackStatusUpdateRequest from(TrackSendRequest request) {
        return new TrackStatusUpdateRequest(request.getStep(), request.getDistance(), request.getCalorie());
    }
}
