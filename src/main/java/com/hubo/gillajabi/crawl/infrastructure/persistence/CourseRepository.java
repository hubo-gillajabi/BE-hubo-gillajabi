package com.hubo.gillajabi.crawl.infrastructure.persistence;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByOriginName(String originName);
}
