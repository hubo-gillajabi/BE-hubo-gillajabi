package com.hubo.gillajabi.search.infrastructure.job;

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
public class BatchSchedulerCourseConfig {

    private final Job courseSearchJob;

    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 0 1 * * ?")  // 매일 새벽 1시에 실행
    public void runJob() throws Exception {
        JobParameters parameters = new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters();
        jobLauncher.run(courseSearchJob, parameters);
    }
}