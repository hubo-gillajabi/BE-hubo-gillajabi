package com.hubo.gillajabi.location.application.dto.request;

import com.hubo.gillajabi.member.domain.entity.Member;
import lombok.Getter;

@Getter
public class LocationHandlingCompleteRequest {
    private String userEmail;

    public LocationHandlingCompleteRequest(Member member){
        this.userEmail = member.getEmail();
    }
}
