package com.hubo.gillajabi.track.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackSpeedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "track_record_id")
    private TrackRecord trackRecord;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String speedData;

    public static TrackSpeedData of(final TrackRecord trackRecord, final String speedData) {
        return new TrackSpeedData(null, trackRecord, speedData);
    }

}
