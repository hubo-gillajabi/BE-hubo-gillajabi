package com.hubo.gillajabi.crawl.domain.entity;

import com.hubo.gillajabi.city.domain.City;
import com.hubo.gillajabi.crawl.domain.constant.CourseLevel;
import com.hubo.gillajabi.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table
public class CourseSection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(length = 150)
    private String shortDescription; // 코스 간단 설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city; // 소속 도시

    @Column
    private Integer distance; // 코스 총 거리 ( 단위 : m )

    @Column
    private Integer totalTimeRequired; // 총 소요 시간

    @Column
    @Enumerated(EnumType.ORDINAL)
    private CourseLevel level; // 난이도

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_id", unique = true)
    private CourseDetail courseDetail;

    @OneToMany(mappedBy = "courseSection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CourseTag> courseTags;
}
