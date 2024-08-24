package com.hubo.gillajabi.track.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackElevationData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @OneToOne
    @JoinColumn(name = "track_record_id")
    private TrackRecord trackRecord;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String elevationData;

    public static TrackElevationData of(final TrackRecord trackRecord, final String elevationData) {
        return new TrackElevationData(null, trackRecord, elevationData);
    }
}
