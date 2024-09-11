package com.hubo.gillajabi.crawl.infrastructure.persistence;

import com.hubo.gillajabi.crawl.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String courseTagName);
}
