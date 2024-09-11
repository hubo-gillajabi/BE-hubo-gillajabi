package com.hubo.gillajabi.crawl.infrastructure.persistence;


import com.hubo.gillajabi.course.domain.entity.CourseTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseTagRepository extends JpaRepository<CourseTag, Long> {

    Optional<CourseTag> findByTagName(String courseTagName);
}
