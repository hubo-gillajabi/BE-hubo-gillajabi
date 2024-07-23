package com.hubo.gillajabi.crawl.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hubo.gillajabi.crawl.domain.entity.CourseTag;

public interface CourseTagRepository extends JpaRepository<CourseTag, Long> {
}
