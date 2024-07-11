package com.hubo.gillajabi.crawl.infrastructure.persistence;

import com.hubo.gillajabi.crawl.domain.entity.Course;
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
}
