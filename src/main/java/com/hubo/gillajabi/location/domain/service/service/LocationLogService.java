package com.hubo.gillajabi.location.domain.service.service;

import com.hubo.gillajabi.global.common.config.RedisConfig;
import com.hubo.gillajabi.location.application.dto.request.LocationHandlingCompleteRequest;
import com.hubo.gillajabi.location.application.dto.request.LocationHandlingRequest;
import com.hubo.gillajabi.location.domain.service.entity.LocationHandlingLog;
import com.hubo.gillajabi.location.infrastructure.persistence.LocationAccessNoticeLogRepository;
import com.hubo.gillajabi.location.infrastructure.persistence.LocationHandlingLogRepository;
import com.hubo.gillajabi.track.infrastructure.exception.TrackException;
import com.hubo.gillajabi.track.infrastructure.exception.TrackExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationLogService {

    private final LocationAccessNoticeLogRepository locationInformationAccessrepository;

    private final LocationHandlingLogRepository locationHandlingLogRepository;

    private final RedisTemplate<String, Long> redisTemplate;

    private static final String REDIS_KEY_PREFIX = "location_handling_log:";

    @Transactional
    public void createLocationHandlingLog(final LocationHandlingRequest locationHandlingRequest){
        final LocationHandlingLog locationHandlingLog = LocationHandlingLog.createByRequest(locationHandlingRequest);
        locationHandlingLogRepository.save(locationHandlingLog);

        final String redisKey = REDIS_KEY_PREFIX + locationHandlingRequest.getRequestUser();
        redisTemplate.opsForValue().set(redisKey, locationHandlingLog.getId());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void completeResponseLocationHandLingLog(final LocationHandlingCompleteRequest request){
        final String redisKey = REDIS_KEY_PREFIX + request.getUserEmail();
        final Long id = redisTemplate.opsForValue().get(redisKey);

        if (id == null) {
            throw new TrackException(TrackExceptionCode.NOT_FOUND_TRACKING);
        }

        final LocationHandlingLog locationHandlingLog = locationHandlingLogRepository.getEntityById(id);
        locationHandlingLog.completeResponse();
        locationHandlingLogRepository.save(locationHandlingLog);

        redisTemplate.delete(redisKey);
    }

}
