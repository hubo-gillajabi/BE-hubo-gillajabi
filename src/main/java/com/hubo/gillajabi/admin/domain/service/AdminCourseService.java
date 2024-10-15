package com.hubo.gillajabi.admin.domain.service;

import com.hubo.gillajabi.admin.application.dto.request.CourseImageRequest;
import com.hubo.gillajabi.course.domain.entity.CourseImage;
import com.hubo.gillajabi.course.domain.service.CourseService;
import com.hubo.gillajabi.course.infrastructure.persistence.CourseImageRepository;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCourseService {

    private final CourseRepository courseRepository;

    private final CourseImageRepository courseImageRepository;

    public void addCourseImages(CourseImageRequest request) {
        Course course = courseRepository.getEntityById(request.courseId());
        course.addImages(request.getImageUrls());

        courseRepository.save(course);
    }

    public void deleteCourseImage(Long id) {
        CourseImage courseImage = courseImageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 이미지가 존재하지 않습니다."));
        courseImage.removeCourse();

        courseImageRepository.delete(courseImage);
    }
}
