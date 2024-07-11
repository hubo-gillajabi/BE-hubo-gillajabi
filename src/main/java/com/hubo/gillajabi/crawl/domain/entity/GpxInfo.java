package com.hubo.gillajabi.crawl.domain.entity;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.hubo.gillajabi.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
public class GpxInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonRawValue  // JSON 문자열로 직접 삽입
    private String gpx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_detail_id", unique = true)
    private CourseDetail courseDetail;

    public static GpxInfo of(String gpx, CourseDetail courseDetail) {
        return new GpxInfo(null, gpx, courseDetail);
    }

    public void updateGpx(String gpx) {
        this.gpx = gpx;
    }
}
