package com.hubo.gillajabi.location.application.dto.request;

import com.hubo.gillajabi.location.domain.service.constant.LocationCollectionType;
import com.hubo.gillajabi.location.domain.service.constant.LocationServiceType;
import com.hubo.gillajabi.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationHandlingRequest {

    private String requestService;
    private String requestUser;
    private String collectionType;

    public static LocationHandlingRequest of(Member member, LocationCollectionType collectionType, LocationServiceType serviceType) {
        return new LocationHandlingRequest(
                serviceType.getDescription(),
                member.getEmail(),
                collectionType.getDescription()
        );
    }
}