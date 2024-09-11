package com.hubo.gillajabi.track.domain.entity;

import com.hubo.gillajabi.global.BaseEntity;
import com.hubo.gillajabi.image.domain.entity.ImageGpsInfo;
import com.hubo.gillajabi.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PhotoPoint extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    private TrackRecord trackRecord;

    private BigDecimal latitude; // 위도

    private BigDecimal longitude; // 경도

    private String photoUrl; // 사진 URL

    public static PhotoPoint createByImageGpsInfo(TrackRecord trackRecord, ImageGpsInfo imageGpsInfo) {
        return new PhotoPoint(null, trackRecord, imageGpsInfo.getLatitude(), imageGpsInfo.getLongitude(), imageGpsInfo.getImageUploadUrl());
    }

    public static void addTrackRecord(Set<PhotoPoint> photoPoints, TrackRecord trackRecord) {
        photoPoints.forEach(photoPoint -> photoPoint.addTrackRecord(trackRecord));
    }

    private void addTrackRecord(TrackRecord trackRecord) {
        this.trackRecord = trackRecord;
    }
}
