package com.hubo.gillajabi.weather.infrastructure.job;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class BatchSchedulerWeatherConfig {

    private final Job processWeatherJob;

    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 20 6 * * ?")  // 매일 오전 6시 20분 실행
    public void runJob() throws Exception{
        JobParameters parameters = new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters();
        jobLauncher.run(processWeatherJob, parameters);
    }
}
