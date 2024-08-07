package com.hubo.gillajabi.global.decorator;


import org.springframework.core.task.TaskDecorator;
import java.util.Map;
import org.slf4j.MDC;


public class MdcTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        // 현재 스레드의 MDC 컨텍스트를 복사
        // 이를 통해 비동기 작업이 원래 요청의 정보를 유지할 수 있음
        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        return () -> {
            try {
                // 새 스레드에서 캡처된 MDC 컨텍스트를 설정
                // null 체크는 현재 스레드에 MDC 컨텍스트가 없는 경우를 대비
                if (contextMap != null) {
                    MDC.setContextMap(contextMap);
                }
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}