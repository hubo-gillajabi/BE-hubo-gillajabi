package com.hubo.gillajabi.track.domain.entity;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.global.BaseEntity;
import com.hubo.gillajabi.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TrackRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "trackRecord", fetch = FetchType.LAZY)
    private TrackGpsData gpsData;

    @OneToOne(mappedBy = "trackRecord", fetch = FetchType.LAZY)
    private TrackElevationData elevationData;

    @OneToOne(mappedBy = "trackRecord", fetch = FetchType.LAZY)
    private TrackSpeedData speedData;

    @OneToMany(mappedBy = "trackRecord", fetch = FetchType.LAZY)
    private List<PhotoPoint> photoPoints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    private Long step;

    private Double distance;

    private Long calorie;

    public static TrackRecord createByMember(final Member member, final Course course) {
        return new TrackRecord(null, null, null, null, null, member, course, 0L,0D,0L);
    }

    public void addDataEntity(TrackGpsData trackGpsData, TrackElevationData trackElevationData,
                              TrackSpeedData trackSpeedData, TrackStatus trackStatus) {
        this.gpsData = trackGpsData;
        this.elevationData = trackElevationData;
        this.speedData = trackSpeedData;
        this.step = trackStatus.getStep();
        this.distance = trackStatus.getDistance();
        this.calorie = trackStatus.getCalorie();
    }
}
