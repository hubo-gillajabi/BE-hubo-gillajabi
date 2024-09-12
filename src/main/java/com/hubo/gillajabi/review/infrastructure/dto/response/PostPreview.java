package com.hubo.gillajabi.review.infrastructure.dto.response;

import com.hubo.gillajabi.city.application.dto.response.CityPreviewDTO;
import com.hubo.gillajabi.course.application.dto.response.CoursePreview;
import com.hubo.gillajabi.global.dto.ImageDTO;
import com.hubo.gillajabi.review.domain.entity.PostSearchDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Getter
@Builder
@AllArgsConstructor
@Setter
public class PostPreview {

    private Long id;

    private String title;

    private List<ImageDTO> images;

    private CoursePreview course;

    private String courseGpsData;

    private CityPreviewDTO city;

    private Long step;

    private BigDecimal distance;

    private Long calorie;

    private MemberPreview member;

    private LocalDate createdTime;

    private boolean bookmarked;

    private Integer courseRating;

    private String content;

    public static PostPreview fromPostSearchDocument(PostSearchDocument postSearchDocument, Set<Long> bookmarkedPostIds) {
        return PostPreview.builder()
                .id(postSearchDocument.getId())
                .title(postSearchDocument.getTitle())
                .images(postSearchDocument.getImageDTOs())
                .course(postSearchDocument.getCoursePreview())
                .city(postSearchDocument.getCityPreview())
                .step(postSearchDocument.getStep())
                .distance(postSearchDocument.getDistance())
                .calorie(postSearchDocument.getCalorie())
                .member(postSearchDocument.getMemberPreview())
                .createdTime(postSearchDocument.getCreatedTime())
                .bookmarked(bookmarkedPostIds.contains(postSearchDocument.getId()))
                .courseRating(postSearchDocument.getCourseRating())
                .content(postSearchDocument.getContent())
                .build();
    }
}

