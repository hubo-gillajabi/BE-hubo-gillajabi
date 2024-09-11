package com.hubo.gillajabi.track.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class TrackSendRequest {

    // 걸음수 (step)
    private Long step;

    private BigDecimal distance;

    private Long calorie;

    // gps 데이터
    private List<List<String>> gpsData;

    // 높이 데이터
    private List<Integer> elevationData;

    // 속도 데이터
    private List<Integer> speedData;
}
