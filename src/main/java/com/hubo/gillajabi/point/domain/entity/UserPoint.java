package com.hubo.gillajabi.point.domain.entity;


import com.hubo.gillajabi.global.type.StatusType;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.point.application.dto.request.UserPointRequest;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "user_points")
@CompoundIndexes({
        @CompoundIndex(name = "course_id", def = "{'courseId': 1}"),
        @CompoundIndex(name = "user_id", def = "{'memberProfile.userId': 1}"),
        @CompoundIndex(name = "location", def = "{'location': '2dsphere'}")
})
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
public class UserPoint {

    @Id
    private String id;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private BigDecimal[] location;

    @Field("content")
    private String content;

    @Field("course_id")
    private Long courseId;

    @Builder.Default
    @Field("image_urls")
    private List<String> imageUrls = new ArrayList<>();

    @Field("member_profile")
    private MemberProfile memberProfile;

    @CreatedDate
    @Field("created_time")
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Field("modified_time")
    private LocalDateTime modifiedTime;

    @Field("status")
    private StatusType status = StatusType.ENABLE;


    public static UserPoint createByMemberAndUserPoint(Member member, UserPointRequest request) {
        return UserPoint.builder()
                .location(new BigDecimal[]{request.latitude(), request.longitude()})
                .content(request.content())
                .courseId(request.courseId())
                .memberProfile(MemberProfile.createByMember(member))
                .imageUrls(request.getImageUrls())
                .build();
    }
}

@Getter
@AllArgsConstructor
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