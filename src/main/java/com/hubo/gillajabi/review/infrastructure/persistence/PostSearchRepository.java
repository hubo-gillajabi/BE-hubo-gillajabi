package com.hubo.gillajabi.review.infrastructure.persistence;

import com.hubo.gillajabi.review.domain.entity.PostSearchDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostSearchRepository extends ElasticsearchRepository<PostSearchDocument, String> {

    @Query("""
            {
              "bool": {
                "must": [
                  {
                    "multi_match": {
                      "query": "?0",
                      "fields": ["title", "course.name", "city.name", "member.nickName"]
                    }
                  }
                ],
                "filter": [
                  {
                    "terms": {
                      "course.id": ?1
                    }
                  }
                ]
              }
            }
            """)
    Page<PostSearchDocument> searchPostsWithBookmarkFilter(String keyword, List<String> bookmarkedCourseIds, Pageable pageable);

    @Query("""
            {
              "multi_match": {
                "query": "?0",
                "fields": ["title", "course.name", "city.name", "member.nickName"]
              }
            }
            """)
    Page<PostSearchDocument> searchPosts(String keyword, Pageable pageable);

    @Query("""
            {
              "bool": {
                "filter": [
                  {
                    "terms": {
                      "course.id": ?0
                    }
                  }
                ]
              }
            }
            """)
    Page<PostSearchDocument> getBookmarkedPosts(List<String> bookmarkedCourseIds, Pageable pageable);

    @Query("""
            {
              "match_all": {}
            }
            """)
    Page<PostSearchDocument> getAllPosts(Pageable pageable);
}