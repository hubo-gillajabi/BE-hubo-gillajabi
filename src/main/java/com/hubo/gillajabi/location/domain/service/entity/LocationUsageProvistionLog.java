package com.hubo.gillajabi.location.domain.service.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/*
 * 위치정보 이용 - 제공 사실 확인 자료 기록
 */
@Table(name = "location_information_usage_provistion_log")
@Entity
@Getter
public class LocationUsageProvistionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 대상 (사용자 xxx@gmail.com)
    @Column(nullable = false, length = 100)
    private String target;

    // 취득 경로 ex ) 앱 내 gps 수집
    @Column(nullable = false, length = 40)
    private String acquisitionPath;

    // 제공 서비스 ex ) 트래킹
    @Column(nullable = false, length = 100)
    private String serviceProvided;

    // 제공 받는자 ( ex ) 유저 이메일 xxx@gmail.com)
    @Column(nullable = false)
    private String requestUser;

    // 제공 방법 ( 이메일로 제공 )
    @Column(nullable = false, length = 100)
    private String provisionMethod;

    @Column(nullable = false)
    private LocalDateTime usageDateTime;
}
