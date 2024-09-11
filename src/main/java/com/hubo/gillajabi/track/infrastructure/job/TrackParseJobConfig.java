package com.hubo.gillajabi.track.infrastructure.job;

import com.hubo.gillajabi.track.domain.entity.*;
import com.hubo.gillajabi.track.infrastructure.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class TrackParseJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final TrackRecordRepository trackRecordRepository;
    private final TrackSnapshotRepository trackSnapshotRepository;
    private final TrackStatusRepository trackStatusRepository;

    private final TrackSpeedDataRepository trackSpeedDataRepository;
    private final TrackGpsDataRepository trackGpsDataRepository;
    private final TrackElevationDataRepository trackElevationDataRepository;

    @Bean(name = "trackSnapshotMigrationJob")
    public Job trackSnapshotMigrationJob() {
        return new JobBuilder("trackSnapshotMigrationJob", jobRepository)
                .start(trackParseStep())
                .next(cleanupStep())
                .build();
    }

    @Bean
    public Step trackParseStep() {
        return new StepBuilder("trackParseStep", jobRepository)
                .<Long, TrackRecord>chunk(1, transactionManager)
                .reader(trackIdReader(null, null, null))
                .processor(trackRecordProcessor(null))
                .writer(trackRecordWriter())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<Long> trackIdReader(
            @Value("#{jobParameters['memberName']}") String memberName,
            @Value("#{jobParameters['memberId']}") Long memberId,
            @Value("#{jobParameters['trackId']}") Long trackId) {
        return new ListItemReader<>(Collections.singletonList(trackId));
    }

    @Bean
    @StepScope
    public ItemProcessor<Long, TrackRecord> trackRecordProcessor(
            @Value("#{jobParameters['memberName']}") String memberName) {
        return trackId -> {
            TrackRecord trackRecord = trackRecordRepository.getEntityById(trackId);

            List<TrackSnapshot> snapshots = trackSnapshotRepository.findByTrackRecordIdOrderByCreatedTimeAsc(trackId);

            if(snapshots.isEmpty()) {
                log.error("트랙 스냅샷이 존재하지 않습니다. trackId: {}", trackId);
                trackRecordRepository.deleteById(trackId);
                return null;
            }

            StringBuilder gpsDataBuilder = new StringBuilder();
            StringBuilder elevationDataBuilder = new StringBuilder();
            StringBuilder speedDataBuilder = new StringBuilder();

            for (TrackSnapshot snapshot : snapshots) {
                gpsDataBuilder.append(snapshot.getGpsData());
                elevationDataBuilder.append(snapshot.getElevationData()).append(",");
                speedDataBuilder.append(snapshot.getSpeedData()).append(",");
            }

            String gpsData = gpsDataBuilder.toString();
            String elevationData = elevationDataBuilder.toString().replaceAll(",$", "");
            String speedData = speedDataBuilder.toString().replaceAll(",$", "");

            TrackGpsData trackGpsData = TrackGpsData.of(trackRecord, gpsData);
            TrackElevationData trackElevationData = TrackElevationData.of(trackRecord, elevationData);
            TrackSpeedData trackSpeedData = TrackSpeedData.of(trackRecord, speedData);

            TrackStatus trackStatus = trackStatusRepository.getEntityById(TrackStatus.getTrackStatusKeyByMember(memberName));
            trackRecord.addDataEntity(trackGpsData, trackElevationData, trackSpeedData, trackStatus);

            return trackRecord;
        };
    }

    @Bean
    public ItemWriter<TrackRecord> trackRecordWriter() {
        CompositeItemWriter<TrackRecord> writer = new CompositeItemWriter<>();
        writer.setDelegates(List.of(
                chunk -> {
                    List<? extends TrackRecord> items = chunk.getItems();
                    for (TrackRecord trackRecord : items) {
                        if (trackRecord != null) {
                            trackRecordRepository.save(trackRecord);
                            trackGpsDataRepository.save(trackRecord.getGpsData());
                            trackElevationDataRepository.save(trackRecord.getElevationData());
                            trackSpeedDataRepository.save(trackRecord.getSpeedData());
                        }
                    }
                }
        ));
        return writer;
    }


    @Bean
    public Step cleanupStep() {
        return new StepBuilder("cleanupStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
                    Long trackId = jobParameters.getLong("trackId");
                    String memberName = jobParameters.getString("memberName");

                    trackSnapshotRepository.deleteByTrackRecordId(trackId);

                    String trackStatusKey = TrackStatus.getTrackStatusKeyByMember(memberName);
                    trackStatusRepository.deleteById(trackStatusKey);

                    log.info("트래킹 배치 작업 success trackId: {} and member.nickname: {}", trackId, memberName);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

}