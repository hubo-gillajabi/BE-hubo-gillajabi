package com.hubo.gillajabi.course.domain.entity;

import co.elastic.clients.elasticsearch.migration.deprecations.Deprecation;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_image")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    public static CourseImage createForCourse(String url, Course course) {
        return new CourseImage(null, url, course);
    }

    public void removeCourse() {
        course.removeImage(this);
        this.course = null;
    }
}
