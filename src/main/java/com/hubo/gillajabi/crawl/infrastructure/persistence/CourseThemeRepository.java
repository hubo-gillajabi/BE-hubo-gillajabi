package com.hubo.gillajabi.crawl.infrastructure.persistence;

import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseThemeRepository extends JpaRepository<CourseTheme, Long> {
    Optional<CourseTheme> findByName(String name);
}
