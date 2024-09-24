package com.hubo.gillajabi.course.infrastructure.persistence;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.course.domain.entity.CourseBookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface CourseBookMarkRepository extends JpaRepository<CourseBookMark, Long> {

    boolean existsByMemberAndCourse(Member member, Course course);

    void deleteByMemberAndCourse(Member member, Course course);

    @Query("SELECT cb.id FROM CourseBookMark cb WHERE cb.member = :member")
    Set<Long> findAllBookMarkedCourseIds(Member member);

}
