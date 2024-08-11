package com.hubo.gillajabi.listener;

import com.hubo.gillajabi.agreement.domain.service.AgreementService;
import com.hubo.gillajabi.agreement.infrastructure.dto.AgreementEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AgreementEventListener {

    private final AgreementService agreementService;

    @EventListener
    @Async
    public void handleAgreementEvent(AgreementEvent event) {
        agreementService.createAgreements(event.member());
    }
}
