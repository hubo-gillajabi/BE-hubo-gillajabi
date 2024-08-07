package com.hubo.gillajabi.listener;

import com.hubo.gillajabi.mail.domain.service.MailService;
import com.hubo.gillajabi.mail.infrastructure.dto.MailEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailEventListener{

    private final MailService mailService;

    @EventListener
    @Async
    public void handleMailEvent(MailEvent event) {
        mailService.sendAgreementMail(event.getMember(), event.getMemberAgreements())
                .exceptionally(ex -> {
                    log.error("메일 전송 실패", ex);
                    return null;
                });
    }

}
