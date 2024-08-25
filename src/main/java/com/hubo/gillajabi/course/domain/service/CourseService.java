package com.hubo.gillajabi.course.domain.service;

import com.hubo.gillajabi.course.domain.entity.CourseElevation;
import com.hubo.gillajabi.course.domain.entity.CourseGps;
import com.hubo.gillajabi.course.infrastructure.dto.response.CourseElevationDto;
import com.hubo.gillajabi.course.infrastructure.dto.response.CourseGpsDto;
import com.hubo.gillajabi.course.infrastructure.persistence.CourseElevationRepository;
import com.hubo.gillajabi.course.infrastructure.persistence.CourseGpsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseGpsRepository courseGpsRepository;
    private final CourseElevationRepository courseElevationRepository;

    public CourseGpsDto getCourseGpsPage(Long courseId) {
        CourseGps courseGps = courseGpsRepository.getEntityByCourseId(courseId);

        return new CourseGpsDto(courseGps);
    }

    public CourseElevationDto getCourseElevationPage(Long courseId) {
        CourseElevation courseElevation = courseElevationRepository.getEntityByCourseId(courseId);

        return new CourseElevationDto(courseElevation);
    }
}
