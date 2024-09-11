package com.hubo.gillajabi.course.domain.entity;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class CourseTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public CourseTag(Course course, Tag tag) {
        this.course = course;
        this.tag = tag;
    }

    public static CourseTag createCourseTag(Course course, Tag tag) {
        return new CourseTag(course, tag);
    }
}