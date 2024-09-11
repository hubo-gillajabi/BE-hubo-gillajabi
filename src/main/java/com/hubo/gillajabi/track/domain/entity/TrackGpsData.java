package com.hubo.gillajabi.track.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TrackGpsData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "track_record_id")
    private TrackRecord trackRecord;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String gpsData;

    public static TrackGpsData of(final TrackRecord trackRecord, final String gpsData) {
        return new TrackGpsData(null, trackRecord, gpsData);
    }
}
