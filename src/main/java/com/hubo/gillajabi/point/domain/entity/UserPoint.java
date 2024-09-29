package com.hubo.gillajabi.point.domain.entity;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.global.BaseEntity;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.point.application.dto.request.UserPointRequest;
import com.hubo.gillajabi.track.domain.entity.PhotoPoint;
import com.hubo.gillajabi.track.domain.entity.TrackRecord;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Table(name = "user_point")
@SQLRestriction(value = "status != 'DELETED'")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserPoint extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String content;

    @OneToMany(mappedBy = "userPoint", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PhotoPoint> photoPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static UserPoint createByMemberAndTrackRecordAndRequest(Member member, UserPointRequest request,
                                                                   TrackRecord trackRecord) {
        UserPoint userPoint = new UserPoint();
        userPoint.member = member;
        userPoint.longitude = request.longitude();
        userPoint.latitude = request.latitude();
        userPoint.content = request.content();
        userPoint.course = trackRecord.getCourse();

        return userPoint;
    }

    public void addPhotoPoint(List<PhotoPoint> photoPoint) {
        this.photoPoint = photoPoint;
    }

    public void isOwner(Member member) {
        if (!this.member.equals(member)) {
            throw new RuntimeException("사용자의 포인트가 아닙니다.");
        }
    }

    @Override
    public void changeStatusToDeleted(){
        super.changeStatusToDeleted();
        this.photoPoint.forEach(PhotoPoint::changeStatusToDeleted);
    }
}
