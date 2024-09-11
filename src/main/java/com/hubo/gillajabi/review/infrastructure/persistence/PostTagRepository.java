package com.hubo.gillajabi.review.infrastructure.persistence;

import com.hubo.gillajabi.review.domain.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
}
