package com.hubo.gillajabi.course.domain.entity;


import com.hubo.gillajabi.crawl.domain.entity.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_elevation")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseElevation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "elevation", nullable = false, columnDefinition = "TEXT")
    private String elevation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    public CourseElevation(final String elevation, final Course course) {
        this.id = null;
        this.elevation = elevation;
        this.course = course;
    }
}