package com.hubo.gillajabi.global.common.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogbackConfig {

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    private static final String LOG_FILE_PATTERN = "logs/application-%s-log-%%d{yyyyMMdd}.csv";
    private static final int MAX_HISTORY = 30;

    @Bean
    public Logger configLogBack() {
        final Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        // CSV 형태의 PatternLayoutEncoder 설정
        final PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(rootLogger.getLoggerContext());
        encoder.setPattern("%d{yyyy-MM-dd'T'HH:mm:ss.SSSXXX},%thread,%level,%logger,%msg%n");
        encoder.start();

        final RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();
        fileAppender.setContext(rootLogger.getLoggerContext());
        fileAppender.setName("FILE");
        fileAppender.setEncoder(encoder);

        final TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<>();
        rollingPolicy.setContext(rootLogger.getLoggerContext());
        rollingPolicy.setParent(fileAppender);
        rollingPolicy.setFileNamePattern(String.format(LOG_FILE_PATTERN, activeProfile));
        rollingPolicy.setMaxHistory(MAX_HISTORY);
        rollingPolicy.start();

        fileAppender.setRollingPolicy(rollingPolicy);
        fileAppender.start();

        rootLogger.addAppender(fileAppender);

        return rootLogger;
    }
}
