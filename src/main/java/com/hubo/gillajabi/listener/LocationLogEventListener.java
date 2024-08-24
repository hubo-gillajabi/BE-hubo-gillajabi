package com.hubo.gillajabi.listener;

import com.hubo.gillajabi.location.application.dto.request.LocationHandlingCompleteRequest;
import com.hubo.gillajabi.location.application.dto.request.LocationHandlingRequest;
import com.hubo.gillajabi.location.domain.service.service.LocationLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocationLogEventListener {

    private final LocationLogService locationLogService;

    @EventListener
    public void createLocationHandlingLog(LocationHandlingRequest locationHandlingRequest){
        locationLogService.createLocationHandlingLog(locationHandlingRequest);
    }

    @EventListener
    @Async
    public void completeLocationHandlingLog(LocationHandlingCompleteRequest locationHandlingCompleteRequest){
        locationLogService.completeResponseLocationHandLingLog(locationHandlingCompleteRequest);
    }
}
