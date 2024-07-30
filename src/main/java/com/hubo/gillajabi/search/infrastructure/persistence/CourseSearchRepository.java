package com.hubo.gillajabi.search.infrastructure.persistence;

import com.hubo.gillajabi.search.domain.document.CourseSearchDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.annotations.Query;

import java.util.List;

public interface CourseSearchRepository extends ElasticsearchRepository<CourseSearchDocument, String> {

    @Query("""
            {
              "nested": {
                "path": "city",
                "query": {
                  "match_phrase_prefix": {
                    "city.name": "?0"
                  }
                }
              }
            }
            """)
    List<CourseSearchDocument> searchByCity(String keyword, Pageable pageable);

    @Query("""
            {
              "nested": {
                "path": "theme",
                "query": {
                  "match_phrase_prefix": {
                    "theme.name": "?0"
                  }
                }
              }
            }
            """)
    List<CourseSearchDocument> searchByTheme(String keyword, Pageable pageable);
}