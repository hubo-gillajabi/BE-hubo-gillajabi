package com.hubo.gillajabi.location.infrastructure.dto.request;

import com.hubo.gillajabi.location.domain.service.constant.LocationCollectionType;
import com.hubo.gillajabi.location.domain.service.constant.LocationServiceType;
import com.hubo.gillajabi.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LocationAccessRequest{

    private String userEmail;

    // 수집방법
    private String collection;

    // 사용 서비스
    private String service;

    public static LocationAccessRequest of(Member member, LocationCollectionType collectionType, LocationServiceType serviceType){
        return new LocationAccessRequest(member.getEmail(), collectionType.getDescription(), serviceType.getDescription());
    }

}
