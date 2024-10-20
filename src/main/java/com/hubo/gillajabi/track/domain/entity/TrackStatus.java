package com.hubo.gillajabi.track.domain.entity;

import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.track.infrastructure.dto.request.TrackStatusUpdateRequest;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@RedisHash(value = "track-status")
public class TrackStatus {

    @Id
    private String id;

    // 걸음수 (step)
    private Long step;

    // 이동 거리 (km)
    private BigDecimal distance;

    // 칼로리 (kcal)
    private Long calorie;

    private LocalDateTime updatedAt;

    private Long trackId;

    public static String getTrackStatusKeyByMember(String memberName) {
        return memberName;
    }

    public static TrackStatus createByMemberAndTrackId(Member member, Long trackId) {
        String trackStatusKey = member.getNickName();
        return new TrackStatus(trackStatusKey, 0L, BigDecimal.ZERO, 0L, LocalDateTime.now(), trackId);
    }

    public void updateByRequest(TrackStatusUpdateRequest trackStatusUpdateRequest) {
        this.step = trackStatusUpdateRequest.getStep();
        this.distance = trackStatusUpdateRequest.getDistance();
        this.calorie = trackStatusUpdateRequest.getCalorie();
        this.updatedAt = LocalDateTime.now();
    }
}
