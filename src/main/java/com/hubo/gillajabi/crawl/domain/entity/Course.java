package com.hubo.gillajabi.crawl.domain.entity;


import com.hubo.gillajabi.city.domain.City;
import com.hubo.gillajabi.crawl.domain.constant.CourseLevel;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseRequest;
import com.hubo.gillajabi.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40, unique = true)
    private String originName;

    private Integer distance;

    private Integer totalTimeRequired;

    @Enumerated(EnumType.STRING)
    private CourseLevel level;

    @Column(length = 500)
    private String shortDescription;

    @Column(length = 40)
    private String courseNumber;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)  //  TODO : eager로 설정 : courseDetail을 조회할 때마다 조회 다른 도메인에서 Course 엔티티에 대해 재정의 할것
    @JoinColumn(name = "detail_id", unique = true)
    private CourseDetail courseDetail;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CourseSection> courseSections;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CourseTag> courseTags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    private CourseTheme courseTheme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;


    public static Course createCourse(final CourseRequest request) {
        return new Course(null, request.getCourseName(), request.getDistance(), request.getTotalRequiredHours(),
                request.getLevel(), request.getShortDescription(), request.getCourseNumber(),
                null, null, null, request.getCourseTheme(), request.getCity());
    }

    public boolean checkUpdate(final CourseRequest request) {
        boolean isUpdated = false;

        if (!this.originName.equals(request.getCourseName())) {
            isUpdated = true;
        }

        if (!this.distance.equals(request.getDistance())) {
            isUpdated = true;
        }

        if (!this.totalTimeRequired.equals(request.getTotalRequiredHours())) {
            isUpdated = true;
        }

        if (this.level != request.getLevel()) {
            isUpdated = true;
        }

        if (!this.shortDescription.equals(request.getShortDescription())) {
            isUpdated = true;
        }

        if (!this.courseNumber.equals(request.getCourseNumber())) {
            isUpdated = true;
        }

        if (!this.city.equals(request.getCity())) {
            isUpdated = true;
        }

        if (!this.courseTheme.equals(request.getCourseTheme())) {
            isUpdated = true;
        }

        return isUpdated;
    }

    public void update(final CourseRequest request) {
        this.originName = request.getCourseName();
        this.distance = request.getDistance();
        this.totalTimeRequired = request.getTotalRequiredHours();
        this.level = request.getLevel();
        this.shortDescription = request.getShortDescription();
        this.courseNumber = request.getCourseNumber();
        this.city = request.getCity();
        this.courseTheme = request.getCourseTheme();
    }


    public void addCourseDetail(CourseDetail existingDetail) {
        this.courseDetail =existingDetail;
    }

    public void addCourseSection(CourseSection courseSection) {
        this.courseSections.add(courseSection);
    }


    public void setCourseDetail(CourseDetail courseDetail) {
        this.courseDetail = courseDetail;
    }
}


