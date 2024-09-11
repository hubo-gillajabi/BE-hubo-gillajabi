package com.hubo.gillajabi.review.domain.entity;


import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.global.BaseEntity;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.review.application.dto.request.PostWriteRequest;
import com.hubo.gillajabi.review.infrastructure.exception.PostException;
import com.hubo.gillajabi.review.infrastructure.exception.PostExceptionCode;
import com.hubo.gillajabi.track.domain.entity.PhotoPoint;
import com.hubo.gillajabi.track.domain.entity.TrackRecord;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction(value = "status != 'DELETED'")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String title = "";

    @Builder.Default
    private String content = "";

    @Builder.Default
    private Long likeCount = 0L;

    @Builder.Default
    private Integer courseRating = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_record_id")
    private TrackRecord trackRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<PostTag> tags = new HashSet<>();

    public static Post fromPostWriteRequest(PostWriteRequest request, TrackRecord trackRecord) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .member(trackRecord.getMember())
                .trackRecord(trackRecord)
                .build();
    }

    public void addCourseTags(List<PostTag> courseTags) {
        this.tags.addAll(courseTags);
        PostTag.addPost(this, courseTags);
    }

    public void addCourseRatting(Integer star) {
        this.courseRating = star;
        this.trackRecord.getCourse().addRating(star);
    }

    @Override
    public void changeStatusToDeleted() {
        super.changeStatusToDeleted();
        this.clearTags();
        this.trackRecord.changeStatusToDisable();
        this.trackRecord.getPhotoPoints().forEach(PhotoPoint::changeStatusToDisable);
    }

    public Course getCourse() {
        return this.trackRecord.getCourse();
    }

    public void checkPostOwner(Member member) {
        if (!this.member.equals(member)) {
            throw new PostException(PostExceptionCode.NOT_OWNER);
        }
    }

    private void clearTags() {
        this.tags.clear();
    }
}
