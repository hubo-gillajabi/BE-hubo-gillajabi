package com.hubo.gillajabi.crawl.domain.entity;


import com.hubo.gillajabi.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CrawlApiResponse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String requestUrl;

    @Column(nullable = false , columnDefinition = "MEDIUMTEXT")
    private String response;

}
