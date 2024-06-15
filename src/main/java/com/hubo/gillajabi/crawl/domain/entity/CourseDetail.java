package com.hubo.gillajabi.crawl.domain.entity;

import com.hubo.gillajabi.crawl.domain.constant.CycleType;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
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

    public static CourseDetail of(final DuruCourseResponse.Course item, final String startPoint,
                                  final String endPoint, final String startPointTransport, final String endPointTransport, final CycleType cycleType) {
        return new CourseDetail(
                null,
                item.getCrsTourInfo(),
                item.getCrsContents(),
                startPoint,
                endPoint,
                startPointTransport,
                endPointTransport,
                Integer.valueOf(item.getCrsTotlRqrmHour()),
                item.getGpxpath(),
                cycleType
        );
    }


    public void update(final DuruCourseResponse.Course item, final String startPoint, final String endPoint,
                       final String startPointTransport, final String endPointTransport, final CycleType cycleType) {
        this.tourInfo = item.getCrsTourInfo();
        this.courseDescription = item.getCrsContents();
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startPointTransport = startPointTransport;
        this.endPointTransport = endPointTransport;
        this.totalTimeRequired = Integer.valueOf(item.getCrsTotlRqrmHour());
        this.cycleType = cycleType;
        this.gpxPath = item.getGpxpath();
    }

    public boolean isCheckUpdate(final DuruCourseResponse.Course item, final String startPoint, final String endPoint,
                                 final String startPointTransport, final String endPointTransport, final CycleType cycleType) {
        return !Objects.equals(this.tourInfo, item.getCrsTourInfo()) ||
                !Objects.equals(this.courseDescription, item.getCrsContents()) ||
                !Objects.equals(this.startPoint, startPoint) ||
                !Objects.equals(this.endPoint, endPoint) ||
                !Objects.equals(this.startPointTransport, startPointTransport) ||
                !Objects.equals(this.endPointTransport, endPointTransport) ||
                !Objects.equals(this.totalTimeRequired, Integer.valueOf(item.getCrsTotlRqrmHour())) ||
                !Objects.equals(this.cycleType, cycleType) ||
                !Objects.equals(this.gpxPath, item.getGpxpath());
    }

}
