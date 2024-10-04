package com.hubo.gillajabi.point.infrastructure.dto.response;

import com.hubo.gillajabi.course.application.dto.response.CoursePreview;
import com.hubo.gillajabi.point.domain.entity.UserPointDocument;
import com.hubo.gillajabi.review.infrastructure.dto.response.MemberPreview;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserPointPreview {
    private String id;
    private String content;
    private Long userPointId;
    private CoursePreview course;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private List<String> imageUrl;
    private MemberPreview member;

    /*
    longgitude가 먼저 들어와야 됨
     */
    public static UserPointPreview from(UserPointDocument userPointDocument) {
        return new UserPointPreview(
                userPointDocument.getId(),
                userPointDocument.getContent(),
                userPointDocument.getUserPointId(),
                CoursePreview.of(userPointDocument.getCourseId(), userPointDocument.getCourseName()),
                BigDecimal.valueOf(userPointDocument.getLocation().getY()),
                BigDecimal.valueOf(userPointDocument.getLocation().getX()),
                userPointDocument.getImageUrls(),
                MemberPreview.of(userPointDocument.getMemberId(), userPointDocument.getMemberNickName(), userPointDocument.getProfileImageUrl())
        );
    }
}
