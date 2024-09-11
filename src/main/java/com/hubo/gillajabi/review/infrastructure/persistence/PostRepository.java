package com.hubo.gillajabi.review.infrastructure.persistence;


import com.hubo.gillajabi.review.domain.entity.Post;
import com.hubo.gillajabi.review.infrastructure.exception.PostException;
import com.hubo.gillajabi.review.infrastructure.exception.PostExceptionCode;
import com.hubo.gillajabi.track.infrastructure.exception.TrackException;
import com.hubo.gillajabi.track.infrastructure.exception.TrackExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static org.hibernate.jpa.HibernateHints.HINT_FETCH_SIZE;

public interface PostRepository extends JpaRepository<Post, Long> {

    boolean existsByTrackRecordId(final Long trackRecordId);

    default Post getPostEntityById(final Long postId){
        if(postId != null){
            return findById(postId).orElseThrow(() -> new PostException(PostExceptionCode.NOT_FOUND_POST));
        }
        return null;
    }


}
