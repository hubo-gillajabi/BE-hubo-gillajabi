package com.hubo.gillajabi.global.common.aop;

import com.hubo.gillajabi.global.common.dto.LoggingFormat;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Slf4j
@Component
public class QueryCounterAop {

    private final ThreadLocal<LoggingFormat> currentLoggingForm;

    public QueryCounterAop() {
        this.currentLoggingForm = new ThreadLocal<>();
    }

    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Object captureConnection(final ProceedingJoinPoint joinPoint) throws Throwable {
        final Object connection = joinPoint.proceed();

        return new ConnectionInterceptor(connection, getCurrentLoggingForm()).getProxy();
    }

    private LoggingFormat getCurrentLoggingForm() {
        if (currentLoggingForm.get() == null) {
            currentLoggingForm.set(new LoggingFormat());
        }

        return currentLoggingForm.get();
    }

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logExecutionTime(final ProceedingJoinPoint joinPoint) throws Throwable {
        final long startTime = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            final long endTime = System.currentTimeMillis();
            final long executionTime = endTime - startTime;
            logAfterApiFinish(executionTime);
        }
    }

    private void logAfterApiFinish(final long executionTime) {
        final LoggingFormat loggingFormat = getCurrentLoggingForm();

        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (isInRequestScope(attributes)) {
            final HttpServletRequest request = attributes.getRequest();
            final HttpServletResponse response = attributes.getResponse();

            loggingFormat.setApiMethod(request.getMethod());
            loggingFormat.setApiUrl(request.getRequestURI());
            loggingFormat.setExecutionTime(executionTime);


            if (response != null) {
                loggingFormat.setStatusCode(response.getStatus());
            }
        }

        log.info("{}", loggingFormat);
        currentLoggingForm.remove();
    }

    private boolean isInRequestScope(final ServletRequestAttributes attributes) {
        return attributes != null;
    }
}
