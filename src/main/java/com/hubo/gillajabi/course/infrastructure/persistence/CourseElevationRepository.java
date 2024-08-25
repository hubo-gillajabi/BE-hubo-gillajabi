package com.hubo.gillajabi.course.infrastructure.persistence;

import com.hubo.gillajabi.course.domain.entity.CourseElevation;
import com.hubo.gillajabi.course.infrastructure.exception.CourseException;
import com.hubo.gillajabi.course.infrastructure.exception.CourseExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseElevationRepository extends JpaRepository<CourseElevation, Long> {

    default CourseElevation getEntityByCourseId(final Long courseId){
        if (courseId != null){
            return findByCourseId(courseId).orElseThrow(() -> new CourseException(CourseExceptionCode.COURSE_ELEVATION_NOT_FOUND));
        }
        return null;
    }


    Optional<CourseElevation> findByCourseId(Long courseId);
}
