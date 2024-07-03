package com.hubo.gillajabi.global.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile("local")
@Slf4j
public class ExecutionTimeLogger {

    @Around("@within(org.springframework.stereotype.Controller) || @within(org.springframework.stereotype.Service) || @within(org.springframework.stereotype.Repository)")
    public final Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();

        Object proceed = joinPoint.proceed();

        long executionTime = System.nanoTime() - start;

        String componentType = getComponentType(joinPoint);

        log.info(String.format("%s -> %s executed in %dns",
                componentType, joinPoint.getSignature(), executionTime));

        return proceed;
    }

    private String getComponentType(ProceedingJoinPoint joinPoint) {
        if (joinPoint.getTarget().getClass().isAnnotationPresent(org.springframework.stereotype.Controller.class)) {
            return "Controller";
        } else if (joinPoint.getTarget().getClass().isAnnotationPresent(org.springframework.stereotype.Service.class)) {
            return "Service";
        } else if (joinPoint.getTarget().getClass().isAnnotationPresent(org.springframework.stereotype.Repository.class)) {
            return "Repository";
        }
        return "Unknown";
    }
}
