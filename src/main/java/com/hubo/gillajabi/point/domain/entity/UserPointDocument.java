package com.hubo.gillajabi.point.domain.entity;

import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.track.domain.entity.PhotoPoint;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "user_points")
@CompoundIndexes({
        @CompoundIndex(name = "user_points_course_id", def = "{'courseId': 1}"),
        @CompoundIndex(name = "user_points_user_id", def = "{'memberProfile.userId': 1}"),
        @CompoundIndex(name = "user_points_user_point_id", def = "{'userPointId': 1}")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class UserPointDocument {

    @Id
    private String id;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE, name = "user_points_location_2dsphere")
    private GeoJsonPoint location;

    @Field("content")
    private String content;

    @Field("course_id")
    private Long courseId;

    @Field("course_name")
    private String courseName;

    @Field("user_point_id")
    private Long userPointId;

    @Field("image_urls")
    private List<String> imageUrls;

    @Field("member_profile")
    private MemberProfile memberProfile;

    /*
    longgitude가 먼저 들어와야 됨
     */
    public static UserPointDocument createByMemberAndUserPoint(final Member member, final UserPoint userPoint) {
        UserPointDocument userPointDocument = new UserPointDocument();
        userPointDocument.memberProfile = MemberProfile.createByMember(member);
        userPointDocument.content = userPoint.getContent();
        userPointDocument.location = new GeoJsonPoint(userPoint.getLongitude().doubleValue(), userPoint.getLatitude().doubleValue());
        userPointDocument.courseId = userPoint.getCourse().getId();
        userPointDocument.courseName = userPoint.getCourse().getOriginName();
        userPointDocument.userPointId = userPoint.getId();
        userPointDocument.imageUrls = userPoint.getPhotoPoint().stream()
                .map(PhotoPoint::getPhotoUrl)
                .toList();
        return userPointDocument;
    }

    public String getMemberNickName() {
        return memberProfile.getNickName();
    }

    public String getProfileImageUrl() {
        return memberProfile.getProfileImageUrl();
    }

    public Long getMemberId() {
        return memberProfile.getMemberId();
    }
}

@Getter
@AllArgsConstructor
@ToString
class MemberProfile {
    @Field("member_id")
    private Long memberId;

    @Field("profile_image_url")
    private String profileImageUrl;

    @Field("nick_name")
    private String nickName;

    public static MemberProfile createByMember(Member member) {
        return new MemberProfile(
                member.getId(),
                member.getProfileImageUrl(),
                member.getNickName());
    }
}