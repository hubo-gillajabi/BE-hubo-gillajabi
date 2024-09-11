package com.hubo.gillajabi.course.domain.service;


import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.course.domain.entity.CourseBookMark;
import com.hubo.gillajabi.course.infrastructure.persistence.CourseBookMarkRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseBookMarkService {

    private final CourseBookMarkRepository courseBookMarkRepository;
    private final CourseRepository courseRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public boolean toggleCourseBookmark(Long courseId, String userName) {
        Member member = memberRepository.getEntityByUserName(userName);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("코스 없음"));

        boolean existingBookmark = courseBookMarkRepository.existsByMemberAndCourse(member, course);

        if (existingBookmark) {
            courseBookMarkRepository.deleteByMemberAndCourse(member, course);
            return false;
        } else {
            CourseBookMark newBookmark = new CourseBookMark(member, course);
            courseBookMarkRepository.save(newBookmark);
            return true;
        }
    }
}
