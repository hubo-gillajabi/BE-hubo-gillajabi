package com.hubo.gillajabi.crawl.infrastructure.persistence;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByOriginName(String originName);

    default Course getEntityById(Long courseId) {
        if (courseId != null) {
            return findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course를 찾을 수 없습니다."));
        }
        return null;
    }

    default Course getEntityByCourseDetail(CourseDetail courseDetail) {
        if (courseDetail != null) {
            return findByCourseDetail(courseDetail).orElseThrow(() -> new IllegalArgumentException("Course를 찾을 수 없습니다."));
        }
        return null;
    }

    Optional<Course> findByCourseDetail(CourseDetail courseDetail);

}
