package com.hubo.gillajabi.post.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String dd;

    // TODO: 2024-08-13

    // 코스 이름
    // 작성자
    // 코스 완주일
    // 코스 완주 시간
    // 코스 완주 거리
    // 사진 포인트 별로
    // 코스 설명
    // 코스 태그

}
