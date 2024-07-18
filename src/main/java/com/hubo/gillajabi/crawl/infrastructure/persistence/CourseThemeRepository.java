package com.hubo.gillajabi.crawl.infrastructure.persistence;

import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseThemeRepository extends JpaRepository<CourseTheme, Long> {
    Optional<CourseTheme> findByName(String name);

    default CourseTheme getEntityById(Long courseThemeId) {
        if(courseThemeId != null) {
            return findById(courseThemeId).orElseThrow(() -> new IllegalArgumentException("코스 테마를 찾을 수 없습니다."));
        }
        return null;
    }

}
