package com.hubo.gillajabi.track.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackEndReqeust {

    @NotNull(message = "trackRecordId 는 필수값 입니다.")
    private Long trackRecordId;
}
