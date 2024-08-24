package com.hubo.gillajabi.location.domain.service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * 위치 정보 수집 사실 열람 - 고지 확인 자료
 */
@Table(name = "location_information_access_notice_log")
@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class LocationAccessNoticeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 취급자 ( ex: hubo )
    @Column(nullable = false, length = 100)
    private String handler;

    // 요청자 ( 사용자  ex: xxx@gmail.com )
    @Column(nullable = false)
    private String requester;

    // 목적 ( ex : 정기 제공, 사용자 요청 제공 등 )
    @Column(nullable = false, length = 100)
    private String purpose;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime datetime;

}
