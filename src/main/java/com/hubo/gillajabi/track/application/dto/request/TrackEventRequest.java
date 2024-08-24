package com.hubo.gillajabi.track.application.dto.request;

import com.hubo.gillajabi.member.domain.entity.Member;

public record TrackEventRequest(Member member, Long trackId) {
}
