package com.hubo.gillajabi.search.infrastructure.persistence;

import com.hubo.gillajabi.search.domain.document.CourseSearchDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.annotations.Query;

import java.util.List;

public interface CourseSearchRepository extends ElasticsearchRepository<CourseSearchDocument, String> {

    @Query("""
            {
                "bool": {
                  "should": [
                    {
                      "match": {
                        "tags": {
                          "query": "?0",
                          "operator": "or"
                        }
                      }
                    },
                    {
                      "nested": {
                        "path": "city",
                        "query": {
                          "prefix": {
                            "city.name": "?0"
                          }
                        }
                      }
                    },
                    {
                      "nested": {
                        "path": "theme",
                        "query": {
                          "prefix": {
                            "theme.name": "?0"
                          }
                        }
                      }
                    }
                  ]
                }
              }
            """)
    List<CourseSearchDocument> searchAllFields(String keyword, Pageable pageable);

}