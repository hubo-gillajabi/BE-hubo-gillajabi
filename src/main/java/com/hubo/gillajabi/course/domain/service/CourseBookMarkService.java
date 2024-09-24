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
    public void toggleCourseBookmark(Long courseId, String userName) {
        Member member = memberRepository.getEntityByUserName(userName);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("코스 없음"));

        boolean existingBookmark = courseBookMarkRepository.existsByMemberAndCourse(member, course);

        if (existingBookmark) {
            throw new RuntimeException("이미 북마크 중입니다");
        }

        CourseBookMark newBookmark = new CourseBookMark(member, course);
        courseBookMarkRepository.save(newBookmark);
    }

    @Transactional
    public void deleteCourseBookmark(Long id, String username) {
        Member member = memberRepository.getEntityByUserName(username);

        CourseBookMark courseBookMark = courseBookMarkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("북마크 없음"));

        if (!courseBookMark.getMember().equals(member)) {
            throw new IllegalArgumentException("북마크 삭제 권한 없음");
        }

        courseBookMark.changeStatusToDeleted();
    }
}
