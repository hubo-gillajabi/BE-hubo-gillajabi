package com.hubo.gillajabi.course.application.resolver;


import com.hubo.gillajabi.course.application.dto.response.CourseElevationPage;
import com.hubo.gillajabi.course.application.dto.response.CourseGpsPage;
import com.hubo.gillajabi.course.domain.service.CourseService;
import com.hubo.gillajabi.course.infrastructure.dto.response.CourseElevationDto;
import com.hubo.gillajabi.course.infrastructure.dto.response.CourseGpsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CourseResolver {

    private final CourseService courseService;

    @QueryMapping
    public CourseGpsPage getCourseGps(@Argument final Long courseId) {
        final CourseGpsDto page =  courseService.getCourseGpsPage(courseId);
        return new CourseGpsPage(page);
    }

    @QueryMapping
    public CourseElevationPage getCourseElevation(@Argument final Long courseId) {
        final CourseElevationDto page =  courseService.getCourseElevationPage(courseId);
        return new CourseElevationPage(page);
    }
}
