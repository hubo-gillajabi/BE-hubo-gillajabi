package com.hubo.gillajabi.track.domain.entity;

import com.hubo.gillajabi.global.BaseEntity;
import com.hubo.gillajabi.image.domain.entity.ImageGpsInfo;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.point.application.dto.request.UserPointRequest;
import com.hubo.gillajabi.point.domain.entity.UserPoint;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLRestriction(value = "status != 'DELETED'")
@Builder
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_point_id")
    private UserPoint userPoint;

    public static PhotoPoint createByImageGpsInfo(final TrackRecord trackRecord, final ImageGpsInfo imageGpsInfo) {
        return new PhotoPoint(null, trackRecord, imageGpsInfo.getLatitude(), imageGpsInfo.getLongitude(), imageGpsInfo.getImageUploadUrl(), null);
    }

    public static void addTrackRecord(final Set<PhotoPoint> photoPoints, final TrackRecord trackRecord) {
        photoPoints.forEach(photoPoint -> photoPoint.addTrackRecord(trackRecord));
    }

    public static List<PhotoPoint> createByImageUrlAndTrackRecordAndUserPoint(UserPointRequest request, TrackRecord trackRecord, UserPoint userPoint) {
        return request.getImageUrls().stream()
                .map(imageUrl -> new PhotoPoint(null, trackRecord, request.latitude(), request.longitude(), imageUrl, userPoint))
                .toList();
    }

    private void addTrackRecord(final TrackRecord trackRecord) {
        this.trackRecord = trackRecord;
    }
}
