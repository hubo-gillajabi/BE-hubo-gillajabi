package com.hubo.gillajabi.course.domain.entity;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_gps")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseGps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gps", nullable = false, columnDefinition = "TEXT")
    private String gps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    public CourseGps(String gps, Course course) {
        this.gps = gps;
        this.course = course;
    }
}
