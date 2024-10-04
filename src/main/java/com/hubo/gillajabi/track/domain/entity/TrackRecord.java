package com.hubo.gillajabi.track.domain.entity;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.global.BaseEntity;
import com.hubo.gillajabi.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@SQLRestriction(value = "status != 'DELETED'")
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
    @Builder.Default
    private List<PhotoPoint> photoPoints = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    private Long step;

    private BigDecimal distance;

    private Long calorie;

    public static TrackRecord createByMember(final Member member, final Course course) {
        return new TrackRecord(null, null, null, null, new ArrayList<>(), member, course, 0L,BigDecimal.ZERO,0L);
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

    public void addPointImages(Set<PhotoPoint> photoPoints) {
        this.photoPoints.addAll(photoPoints);
        PhotoPoint.addTrackRecord(photoPoints, this);
    }

    public void addPhotoPoints(Set<PhotoPoint> photoPoints) {
        this.photoPoints.addAll(photoPoints);
        PhotoPoint.addTrackRecord(photoPoints, this);
    }

    public void checkTrackOwner(Member member) {
        if (!this.member.equals(member)) {
            throw new IllegalArgumentException("트랙의 소유자가 아닙니다.");
        }
    }

    public void addPhotoPoint(List<PhotoPoint> photoPoint) {
        this.photoPoints.addAll(photoPoint);
    }
}
