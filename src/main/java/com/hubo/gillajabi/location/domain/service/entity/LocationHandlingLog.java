package com.hubo.gillajabi.location.domain.service.entity;

import com.hubo.gillajabi.location.application.dto.request.LocationHandlingRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * LocationInformationHandlingLog
 * 위치 정보 수집 확인 자료
 */
@Entity
@Table(name = "location_information_handling_log")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationHandlingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 수집자 ( ex: hubo )
    @Column(nullable = false, length = 50)
    private String collector;

    // 요청 서비스 ( ex : 트래킹 )
    @Column(nullable = false, length = 100)
    private String requestService;

    // 요청 사용자 ( ex : xxxx@gmail.com)
    @Column(nullable = false)
    private String requestUser;

    // 수집 방법
    @Column(nullable = false, length = 100)
    private String collectionMethod;

    // 수집 시작 시간
    @Column(nullable = false)
    private LocalDateTime requestTime;

    // 수집 종료 시간
    private LocalDateTime responseTime;


    private LocationHandlingLog(String collector, String requestService, String requestUser, String collectionMethod, LocalDateTime requestTime) {
        this.collector = collector;
        this.requestService = requestService;
        this.requestUser = requestUser;
        this.collectionMethod = collectionMethod;
        this.requestTime = requestTime;
    }

    public static LocationHandlingLog createByRequest(LocationHandlingRequest request) {
        return new LocationHandlingLog(
                "hubo",
                request.getRequestService(),
                request.getRequestUser(),
                request.getCollectionType(),
                LocalDateTime.now()
        );
    }

    public void completeResponse() {
        this.responseTime = LocalDateTime.now();
    }
}
