package com.hubo.gillajabi.course.domain.entity;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.global.BaseEntity;
import com.hubo.gillajabi.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class CourseBookMark extends BaseEntity {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public CourseBookMark(Member member, Course course) {
        this.member = member;
        this.course = course;
    }
}
