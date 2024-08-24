package com.hubo.gillajabi.track.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubo.gillajabi.track.infrastructure.dto.request.TrackSnapshotRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TrackSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long trackRecordId;

    @Column(nullable = false , columnDefinition = "MEDIUMTEXT")
    private String gpsData; // gps

    @Column(columnDefinition = "MEDIUMTEXT")
    private String elevationData;  // 높이

    @Column(columnDefinition = "MEDIUMTEXT")
    private String speedData; // 속도

    @Column(nullable = false)
    private LocalDateTime createdTime;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static TrackSnapshot createByRequest(TrackSnapshotRequest trackSnapshotRequest) {
        return new TrackSnapshot(null,
                trackSnapshotRequest.getTrackRecordId(),
                convertGpsDataToString(trackSnapshotRequest.getGpsData()),
                convertListToString(trackSnapshotRequest.getElevationData()),
                convertListToString(trackSnapshotRequest.getSpeedData()),
                LocalDateTime.now());
    }

    private static String convertListToString(List<?> data) {
        return data.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    private static String convertGpsDataToString(List<List<String>> gpsData) {
        String fullString = gpsData.toString();
        return fullString.substring(1, fullString.length() - 1);
    }

}
