package com.hubo.gillajabi.listener;

import com.hubo.gillajabi.location.application.dto.request.LocationHandlingCompleteRequest;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.domain.service.MemberService;
import com.hubo.gillajabi.track.application.dto.request.TrackEventRequest;
import com.hubo.gillajabi.track.domain.entity.TrackStatus;
import com.hubo.gillajabi.track.infrastructure.persistence.TrackStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisKeyExpirationListener implements MessageListener {

    private static final String TRACK_STATUS_TIMER = "track-status-timer:";

    private final TrackStatusRepository trackStatusRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final MemberService memberService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        log.info("감지된 redis key{}", expiredKey);

        if (expiredKey.startsWith(TRACK_STATUS_TIMER)) {
            String memberName = expiredKey.substring(TRACK_STATUS_TIMER.length());
            Member member = memberService.findByNickName(memberName);

            eventPublisher.publishEvent(new LocationHandlingCompleteRequest(member));

            TrackStatus trackStatus = trackStatusRepository.getEntityById(member.getNickName());
            Long trackId = trackStatus.getTrackId();

            TrackEventRequest request = new TrackEventRequest(member, trackId);
            eventPublisher.publishEvent(request);
        }
    }
}