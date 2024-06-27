package com.hubo.gillajabi.global.common.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class LoggingFormat {

    private String apiUrl;

    private String apiMethod;

    private Long queryCounts = 0L;

    private Long queryTime = 0L;

    private Long redisQueryCounts = 0L;

    private HttpStatus statusCode;

    private Long executionTime;

    public void setApiUrl(final String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void setApiMethod(final String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public void queryCountUp() {
        queryCounts++;
    }

    public void addQueryTime(final Long queryTime) {
        this.queryTime += queryTime;
    }

    public void redisQueryCountUp() {
        redisQueryCounts++;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode = HttpStatus.valueOf(statusCode);
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}
