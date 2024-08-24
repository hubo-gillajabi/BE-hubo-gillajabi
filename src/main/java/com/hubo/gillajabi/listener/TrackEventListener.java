package com.hubo.gillajabi.listener;

import com.hubo.gillajabi.track.application.dto.request.TrackEventRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class TrackEventListener {

    private JobRepository jobRepository;

    @Qualifier("trackSnapshotMigrationJob")
    private Job trackSnapshotMigrationJob;

    public TrackEventListener (JobRepository jobRepository, Job trackSnapshotMigrationJob) {
        this.jobRepository = jobRepository;
        this.trackSnapshotMigrationJob = trackSnapshotMigrationJob;
    }

    @EventListener
    @Async
    public void handleTrackEvent(final TrackEventRequest request) {
        log.info("트랙 이벤트를 처리합니다.");
        try {
            TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
            jobLauncher.setJobRepository(jobRepository);
            jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
            jobLauncher.afterPropertiesSet();

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("memberName", request.member().getNickName())
                    .addLong("memberId", request.member().getId())
                    .addLong("trackId", request.trackId())
                    .toJobParameters();

            jobLauncher.run(trackSnapshotMigrationJob, jobParameters);
        } catch (Exception e) {
            log.error("배치 작업 실행 중 오류 발생", e);
        }

        // TODO : 트래킹 종료 알림 발송
    }
}