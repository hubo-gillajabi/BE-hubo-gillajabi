package com.hubo.gillajabi.crawl.infrastructure.persistence;

import com.hubo.gillajabi.crawl.domain.entity.CourseSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseSectionRepository extends JpaRepository<CourseSection, Long> {
}
