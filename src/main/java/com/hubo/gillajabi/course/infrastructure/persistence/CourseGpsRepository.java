package com.hubo.gillajabi.course.infrastructure.persistence;

import com.hubo.gillajabi.course.domain.entity.CourseGps;
import com.hubo.gillajabi.course.infrastructure.exception.CourseException;
import com.hubo.gillajabi.course.infrastructure.exception.CourseExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseGpsRepository extends JpaRepository<CourseGps, Long> {

    default CourseGps getEntityByCourseId(final Long courseId) {
        if(courseId != null){
            return findByCourseId(courseId).orElseThrow(() -> new CourseException(CourseExceptionCode.COURSE_GPS_NOT_FOUND));
        }
        return null;
    }

    Optional<CourseGps> findByCourseId(Long courseId);
}
