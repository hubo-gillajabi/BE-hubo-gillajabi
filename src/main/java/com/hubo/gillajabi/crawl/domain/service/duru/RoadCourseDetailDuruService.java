package com.hubo.gillajabi.crawl.domain.service.duru;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseDetail;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseDetailRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseDetailRepository;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;
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
public class RoadCourseDetailDuruService {

    private final CourseDetailRepository courseDetailRepository;
    private final CourseRepository courseRepository;

    private static final Pattern startPattern = Pattern.compile("(?:- )?시점(?:: | :| ) (.*?)(?:<br>|\\s)(?:교통편\\)|\uAD50\uD1B5\uD3B8\\)) (.*?)(?:<br>|\\s)");
    private static final Pattern endPattern = Pattern.compile("(?:- )?종점(?:: | :| ) (.*?)(?:<br>|\\s)(?:교통편\\)|\uAD50\uD1B5\uD3B8\\)) (.*?)(?:<br>|\\s)");

    public List<CourseDetail> saveDuruCourseDetail(final List<ApiCourseResponse.Course> responseItems, final List<Course> courses) {
        List<CourseDetail> courseDetails = new ArrayList<>();
        responseItems.forEach(item -> {
            final Course course = findCourseByName(courses, item.getCrsKorNm());
            final CourseDetailRequestDTO courseDetailRequestDTO = buildCourseDetailRequest(item);

            final CourseDetail courseDetail = createOrUpdateDetail(courseDetailRequestDTO, course);
            updateCourseDetailByCourse(course, courseDetail);
            courseDetails.add(courseDetail);
        });
        return courseDetails;
    }

    private CourseDetailRequestDTO buildCourseDetailRequest(final ApiCourseResponse.Course item) {
        final String startPoint = extractPoint(item.getTravelerinfo(), startPattern, 1);
        final String endPoint = extractPoint(item.getTravelerinfo(), endPattern, 1);
        final String startPointTransport = extractPoint(item.getTravelerinfo(), startPattern, 2);
        final String endPointTransport = extractPoint(item.getTravelerinfo(), endPattern, 2);

        return CourseDetailRequestDTO.of(item, startPoint, endPoint, startPointTransport, endPointTransport);
    }

    private String extractPoint(final String info, final Pattern pattern, final int group) {
        final Matcher matcher = pattern.matcher(info);
        return matcher.find() ? matcher.group(group) : null;
    }

    private CourseDetail createOrUpdateDetail(final CourseDetailRequestDTO request, final Course course) {
        final CourseDetail existingDetail = course.getCourseDetail();
        return existingDetail != null ? updateExistingDetail(existingDetail, request) : createNewCourseDetail(request, course);
    }

    private CourseDetail updateExistingDetail(final CourseDetail detail, final CourseDetailRequestDTO request) {
        if (detail.isCheckUpdate(request)) {
            detail.update(request);
            return courseDetailRepository.save(detail);
        }
        return detail;
    }

    private CourseDetail createNewCourseDetail(final CourseDetailRequestDTO request, final Course course) {
        final CourseDetail newDetail = CourseDetail.createCourseDetail(request);
        course.addCourseDetail(newDetail);
        return courseDetailRepository.save(newDetail);
    }

    private Course findCourseByName(final List<Course> courses, final String courseName) {
        return courses.stream()
                .filter(c -> c.getOriginName().equals(courseName))
                .findFirst()
                .orElseThrow(() -> new CrawlException(0, "코스를 찾을 수 없습니다. 이름: " + courseName));
    }

    private void updateCourseDetailByCourse(final Course course, final CourseDetail courseDetail) {
        course.setCourseDetail(courseDetail);
        courseRepository.save(course);
    }
}
