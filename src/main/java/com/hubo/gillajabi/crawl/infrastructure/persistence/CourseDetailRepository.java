package com.hubo.gillajabi.crawl.infrastructure.persistence;

import com.hubo.gillajabi.crawl.domain.entity.CourseDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDetailRepository  extends JpaRepository<CourseDetail, Long> {
}
