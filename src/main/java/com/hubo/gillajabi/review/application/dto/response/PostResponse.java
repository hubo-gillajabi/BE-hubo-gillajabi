package com.hubo.gillajabi.review.application.dto.response;

import com.hubo.gillajabi.global.dto.ImageDTO;
import com.hubo.gillajabi.review.domain.entity.Post;
import com.hubo.gillajabi.review.infrastructure.dto.response.MemberPreview;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

    private Long id;

    private String title;

    private String content;

    private Long step;

    private BigDecimal distance;

    private Long calorie;

    private String elevationData;

    private List<ImageDTO> images;

    private MemberPreview member;

    private boolean bookmarked;

    public static PostResponse createByEntity(final Post post, final boolean bookmarked) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getTrackRecord().getStep(),
                post.getTrackRecord().getDistance(),
                post.getTrackRecord().getCalorie(),
                post.getTrackRecord().getElevationData().getElevationData(),
                post.getTrackRecord().getPhotoPoints().stream()
                        .map(ImageDTO::createByEntity)
                        .toList(),
                MemberPreview.createByEntity(post.getMember()),
                bookmarked
        );
    }
}
