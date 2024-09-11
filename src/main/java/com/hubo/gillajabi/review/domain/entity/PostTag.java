package com.hubo.gillajabi.review.domain.entity;

import com.hubo.gillajabi.crawl.domain.entity.Tag;
import com.hubo.gillajabi.track.domain.entity.TrackRecord;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    private PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }

    public static PostTag createPostTag(Tag tag) {
        return new PostTag(null, tag);
    }

    public static void addPost(Post post, List<PostTag> courseTags) {
        courseTags.forEach(courseTag -> courseTag.addPost(post));
    }

    private void addPost(Post post) {
        this.post = post;
    }
}