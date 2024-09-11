package com.hubo.gillajabi.track.infrastructure.timer;

import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.track.infrastructure.exception.TrackException;
import com.hubo.gillajabi.track.infrastructure.exception.TrackExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class TrackStatusTimer {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String TIMER_KEY_PREFIX = "track-status-timer:";
    private static final long ACTIVE_EXPIRATION_TIME_MINUTES = 30;
    private static final long PAUSED_EXPIRATION_TIME_HOURS = 2;
    private static final long STOP_EXPIRATION_TIME_SECONDS = 2;

    private static final String ACTIVE_STATUS = "active";
    private static final String PAUSED_STATUS = "paused";
    private static final String STOPPED_STATUS = "stopped";

    public void startTimerForMember(final String username) {
        setTimerForMember(username, ACTIVE_EXPIRATION_TIME_MINUTES, TimeUnit.MINUTES, ACTIVE_STATUS);
    }

    public void pauseTimerForMember(final String username) {
        setTimerForMember(username, PAUSED_EXPIRATION_TIME_HOURS, TimeUnit.HOURS, PAUSED_STATUS);
    }

    public void restartTimerForMember(final String username) {
        setTimerForMember(username, ACTIVE_EXPIRATION_TIME_MINUTES, TimeUnit.MINUTES, ACTIVE_STATUS);
    }

    public void stopTimerForMember(final String username) {
        setTimerForMember(username, STOP_EXPIRATION_TIME_SECONDS, TimeUnit.SECONDS, STOPPED_STATUS);
    }

    private void setTimerForMember(final String username, final long expirationTime, final TimeUnit timeUnit, final String status) {
        final String redisKey = TIMER_KEY_PREFIX + username;
        redisTemplate.opsForValue().set(redisKey, status);
        redisTemplate.expire(redisKey, expirationTime, timeUnit);
    }

    public boolean isTrackActive(final String username) {
        final String redisKey = TIMER_KEY_PREFIX + username;
        final String status = redisTemplate.opsForValue().get(redisKey);

        if (status == null) {
            throw new TrackException(TrackExceptionCode.NOT_TRACKING);
        }

        return ACTIVE_STATUS.equals(status);
    }

    public void isTracking(String username) {
        final String redisKey = TIMER_KEY_PREFIX + username;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
            throw new TrackException(TrackExceptionCode.ALREADY_TRACKING);
        }
    }
}
