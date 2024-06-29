package com.hubo.gillajabi.crawl.domain.entity;

import com.hubo.gillajabi.crawl.domain.constant.CycleType;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseDetailRequest;
import com.hubo.gillajabi.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 500)
    private String tourInfo; // 코스 정보 (관광 정보)

    @Column(columnDefinition = "TEXT")
    private String courseDescription; // 코스 설명

    @Column(length = 50)
    private String startPoint; // 시작점

    @Column(length = 50)
    private String endPoint; // 종점

    @Column(length = 500)
    private String startPointTransport; // 시작점 교통 정보

    @Column(length = 500)
    private String endPointTransport; // 종점 교통 정보

    private Integer totalTimeRequired; // 총 소요 시간

    @Column(length = 500)
    private String gpxPath;

    @Enumerated(EnumType.STRING)
    private CycleType cycleType; // 순환 여부

    public static CourseDetail createCourseDetail(final CourseDetailRequest request) {
        return new CourseDetail(
                null,
                request.getTourInfo(),
                request.getCourseDescription(),
                request.getStartPoint(),
                request.getEndPoint(),
                request.getStartPointTransport(),
                request.getEndPointTransport(),
                request.getTotalTimeRequired(),
                request.getGpxPath(),
                request.getCycleType()
        );
    }

    public void update(final CourseDetailRequest request) {
        this.tourInfo = request.getTourInfo();
        this.courseDescription = request.getCourseDescription();
        this.startPoint = request.getStartPoint();
        this.endPoint = request.getEndPoint();
        this.startPointTransport = request.getStartPointTransport();
        this.endPointTransport = request.getEndPointTransport();
        this.totalTimeRequired = request.getTotalTimeRequired();
        this.gpxPath = request.getGpxPath();
        this.cycleType = request.getCycleType();
    }

    public boolean isCheckUpdate(final CourseDetailRequest request) {
        return !Objects.equals(this.tourInfo, request.getTourInfo())
                || !Objects.equals(this.courseDescription, request.getCourseDescription())
                || !Objects.equals(this.startPoint, request.getStartPoint())
                || !Objects.equals(this.endPoint, request.getEndPoint())
                || !Objects.equals(this.startPointTransport, request.getStartPointTransport())
                || !Objects.equals(this.endPointTransport, request.getEndPointTransport())
                || !Objects.equals(this.totalTimeRequired, request.getTotalTimeRequired())
                || !Objects.equals(this.gpxPath, request.getGpxPath())
                || this.cycleType != request.getCycleType();
    }

}
