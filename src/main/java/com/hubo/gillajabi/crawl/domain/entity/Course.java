package com.hubo.gillajabi.crawl.domain.entity;


import com.hubo.gillajabi.crawl.domain.constant.CourseLevel;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import com.hubo.gillajabi.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


    public static Course createCourse(final String originName, final Integer distance, final Integer totalTimeRequired, final CourseLevel level,
                                      final String shortDescription, final String courseNumber, final City city, final CourseTheme courseTheme) {
        return new Course(null, originName, distance, totalTimeRequired, level, shortDescription, courseNumber,
                null, null, null, courseTheme, city);
    }

    public boolean checkUpdate(final DuruCourseResponse.Course course, final City city, final CourseLevel level,
                               final String shortDescription, final String courseNumber, final CourseTheme courseTheme) {
        boolean isUpdated = false;

        if (!this.originName.equals(course.getCrsKorNm())) {
            isUpdated = true;
        }

        if (!this.distance.equals(Integer.valueOf(course.getCrsDstnc()))) {
            isUpdated = true;
        }

        if (!this.totalTimeRequired.equals(Integer.valueOf(course.getCrsTotlRqrmHour()))) {
            isUpdated = true;
        }

        if (this.level != level) {
            isUpdated = true;
        }

        if (!this.shortDescription.equals(shortDescription)) {
            isUpdated = true;
        }

        if (!this.courseNumber.equals(courseNumber)) {
            isUpdated = true;
        }

        if (!this.city.equals(city)) {
            isUpdated = true;
        }

        if (!this.courseTheme.equals(courseTheme)) {
            isUpdated = true;
        }

        return isUpdated;
    }

    public void update(final DuruCourseResponse.Course item, final City city, final CourseLevel level,
                       final String shortDescription, final String courseNumber, final CourseTheme courseTheme) {
        this.originName = item.getCrsKorNm();
        this.distance = Integer.valueOf(item.getCrsDstnc());
        this.totalTimeRequired = Integer.valueOf(item.getCrsTotlRqrmHour());
        this.level = level;
        this.shortDescription = shortDescription;
        this.courseNumber = courseNumber;
        this.city = city;
        this.courseTheme = courseTheme;
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


