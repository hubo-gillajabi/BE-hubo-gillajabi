package com.hubo.gillajabi.crawl.domain.service;

import com.hubo.gillajabi.crawl.domain.constant.CycleType;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseDetail;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseDetailRepository;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import com.hubo.gillajabi.crawl.infrastructure.exception.CrawlException;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Transactional
@RequiredArgsConstructor
public class CourseDetailService {

    private final CourseDetailRepository courseDetailRepository;

    private final CourseRepository courseRepository;

    private static final Pattern startPattern =
            Pattern.compile("(?:- )?시점(?:: | :| ) (.*?)(?:<br>|\\s)(?:교통편\\)|\uAD50\uD1B5\uD3B8\\)) (.*?)(?:<br>|\\s)");
    private static final Pattern endPattern =
            Pattern.compile("(?:- )?종점(?:: | :| ) (.*?)(?:<br>|\\s)(?:교통편\\)|\uAD50\uD1B5\uD3B8\\)) (.*?)(?:<br>|\\s)");

    public List<CourseDetail> saveDuruCourseDetail(final List<DuruCourseResponse.Course> responseItems, final List<Course> courses) {
        List<CourseDetail> courseDetails = new ArrayList<>();
        for (DuruCourseResponse.Course item : responseItems) {
            final Course course = findCourseByName(courses, item.getCrsKorNm());
            final CycleType cycleType = CycleType.fromValue(item);

            final String startPoint = extractStartPoint(item);
            final String endPoint = extractEndPoint(item);
            final String startPointTransport = extractStartPointTransport(item);
            final String endPointTransport = extractEndPointTransport(item);

            final CourseDetail courseDetail = createOrUpdateDetail(item, course, startPoint, endPoint, startPointTransport, endPointTransport, cycleType);
            updateCourseDetailByCourse(course, courseDetail);
            courseDetails.add(courseDetail);
        }
        return courseDetails;
    }

    private String extractStartPoint(final DuruCourseResponse.Course item) {
        final Matcher startMatcher = startPattern.matcher(item.getTravelerinfo());
        return startMatcher.find() ? startMatcher.group(1) : null;
    }

    private String extractEndPoint(final DuruCourseResponse.Course item) {
       final Matcher endMatcher = endPattern.matcher(item.getTravelerinfo());
        return endMatcher.find() ? endMatcher.group(1) : null;
    }

    private String extractStartPointTransport(final DuruCourseResponse.Course item) {
        final Matcher matcher = CourseDetailService.startPattern.matcher(item.getTravelerinfo());
        return matcher.find() ? matcher.group(1) : null;
    }

    private String extractEndPointTransport(final DuruCourseResponse.Course item) {
        final Matcher matcher = CourseDetailService.endPattern.matcher(item.getTravelerinfo());
        return matcher.find() ? matcher.group(2) : null;
    }

    private CourseDetail createOrUpdateDetail(DuruCourseResponse.Course item, Course course, String startPoint,
                                              String endPoint, String startPointTransport, String endPointTransport, CycleType cycleType) {
        final CourseDetail existingDetail = course.getCourseDetail();

        if (existingDetail != null) {
            return updateExistingDetail(existingDetail, item, startPoint, endPoint, startPointTransport, endPointTransport, cycleType);
        } else {
            return createNewCourseDetail(item, startPoint, endPoint, startPointTransport, endPointTransport, cycleType, course);
        }
    }

    private CourseDetail updateExistingDetail(CourseDetail detail, DuruCourseResponse.Course item, String startPoint, String endPoint,
                                              String startPointTransport, String endPointTransport, CycleType cycleType) {
        if (detail.isCheckUpdate(item, startPoint, endPoint, startPointTransport, endPointTransport, cycleType)) {
            detail.update(item, startPoint, endPoint, startPointTransport, endPointTransport, cycleType);
            return courseDetailRepository.save(detail);
        }
        return detail;
    }

    private CourseDetail createNewCourseDetail(DuruCourseResponse.Course item, String startPoint, String endPoint,
                                               String startPointTransport, String endPointTransport, CycleType cycleType, Course course) {
        CourseDetail newDetail = CourseDetail.of(item, startPoint, endPoint, startPointTransport, endPointTransport, cycleType);
        course.addCourseDetail(newDetail);
        return courseDetailRepository.save(newDetail);
    }

    private Course findCourseByName(final List<Course> courses, final String courseName) {
        return courses.stream()
                .filter(c -> c.getOriginName().equals(courseName))
                .findFirst()
                .orElseThrow(() -> new CrawlException(0, "CourseDetailService.saveDuruCourseDetail: course 를 찾을 수 없음"));
    }

    private void updateCourseDetailByCourse(final Course course, final CourseDetail courseDetail) {
        course.setCourseDetail(courseDetail);
        courseRepository.save(course);
    }
}
