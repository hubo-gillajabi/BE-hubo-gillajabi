package com.hubo.gillajabi.point.domain.service;

import com.hubo.gillajabi.course.domain.entity.CourseBookMark;
import com.hubo.gillajabi.course.infrastructure.persistence.CourseBookMarkRepository;
import com.hubo.gillajabi.global.dto.CursorPageInfo;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.point.application.dto.response.UserPointResponse;
import com.hubo.gillajabi.point.domain.entity.UserPointDocument;
import com.hubo.gillajabi.point.infrastructure.dto.response.UserPointPreview;
import com.hubo.gillajabi.point.infrastructure.persistence.UserPointDocumentRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserPointSearchService {

    private final UserPointDocumentRepository userPointDocumentRepository;

    private final CourseBookMarkRepository courseBookMarkRepository;

    private final MemberRepository memberRepository;

    public UserPointResponse getUserPointPreviews(@Nullable final Boolean bookmarked, @Nullable final String cursor, int limit, final String username) {
        final Member member = memberRepository.getEntityByUserName(username);
        Pageable pageable = createPageable(limit);

        Slice<UserPointDocument> userPointsSlice = fetchUserPointDocuments(bookmarked, cursor, member, pageable);

        return createUserPointResponse(userPointsSlice);
    }

    private Pageable createPageable(int limit) {
        return PageRequest.of(0, limit, Sort.by(Sort.Direction.ASC, "_id"));
    }

    private Slice<UserPointDocument> fetchUserPointDocuments(Boolean bookmarked, String cursor, Member member, Pageable pageable) {
        if (bookmarked) {
            List<Long> bookmarkedCourseIds =  courseBookMarkRepository.findCourseIdsByMember(member);
            return userPointDocumentRepository.findByCourseIdInWithCursor(bookmarkedCourseIds, cursor, pageable);
        } else {
            return userPointDocumentRepository.findAllWithCursor(cursor, pageable);
        }
    }

    public UserPointResponse getUserPointPreviewsByCourse(@Nullable final Long courseId, final Double latitude,
                                                          final Double longitude, final Double radius) {
        GeoJsonPoint geoJsonPoint = new GeoJsonPoint(longitude, latitude);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        List<UserPointDocument> userPointsSlice = fetchUserPointDocumentsByCourse(courseId, geoJsonPoint, distance);
        return createUserPointResponse(userPointsSlice);
    }

    private List<UserPointDocument> fetchUserPointDocumentsByCourse(Long courseId, GeoJsonPoint point, Distance distance) {
        if (courseId != null) {
            return userPointDocumentRepository.findByCourseIdAndLocationNear(courseId, point, distance);
        } else {
            return userPointDocumentRepository.findByLocationNear(point, distance);
        }
    }

    private UserPointResponse createUserPointResponse(Slice<UserPointDocument> userPointsSlice) {
        List<UserPointDocument> userPoints = userPointsSlice.getContent();
        boolean hasNext = userPointsSlice.hasNext();

        String nextCursor = hasNext && !userPoints.isEmpty() ? userPoints.get(userPoints.size() - 1).getId() : null;

        List<UserPointPreview> postPreviewResponses = userPoints.stream()
                .map(UserPointPreview::from)
                .collect(Collectors.toList());

        CursorPageInfo cursorPageInfo = new CursorPageInfo(nextCursor, hasNext);

        return new UserPointResponse(postPreviewResponses, cursorPageInfo);
    }

    private UserPointResponse createUserPointResponse(List<UserPointDocument> userPoints) {
        List<UserPointPreview> postPreviewResponses = userPoints.stream()
                .map(UserPointPreview::from)
                .collect(Collectors.toList());

        return new UserPointResponse(postPreviewResponses);
    }
}