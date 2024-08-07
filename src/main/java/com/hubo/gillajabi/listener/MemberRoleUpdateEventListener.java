package com.hubo.gillajabi.listener;

import com.hubo.gillajabi.login.application.service.MemberRoleService;
import com.hubo.gillajabi.member.infrastructure.dto.MemberRoleUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberRoleUpdateEventListener {

    private final MemberRoleService memberRoleService;

    @EventListener
    @Async
    public void handleMemberRoleUpdateEvent(MemberRoleUpdateEvent event) {
        boolean result = memberRoleService.updateRole(event.getMember(), event.getMemberAgreements());
        if(!result){
            log.error("ROle 업데이트 실패: {}", event.getMember().getNickName());
        }

    }
}
