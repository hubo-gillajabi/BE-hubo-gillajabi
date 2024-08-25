package com.hubo.gillajabi.course.infrastructure.job;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubo.gillajabi.course.domain.entity.CourseElevation;
import com.hubo.gillajabi.course.domain.entity.CourseGps;
import com.hubo.gillajabi.course.infrastructure.dto.GpxInfo;
import com.hubo.gillajabi.course.infrastructure.dto.ParsedCourseData;
import com.hubo.gillajabi.course.infrastructure.persistence.CourseElevationRepository;
import com.hubo.gillajabi.course.infrastructure.persistence.CourseGpsRepository;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseDetail;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseDetailRepository;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import com.hubo.gillajabi.crawl.infrastructure.persistence.GpxInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class CourseParseJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final DataSource dataSource;

    private final CourseRepository courseRepository;
    private final CourseDetailRepository courseDetailRepository;

    private final CourseGpsRepository courseGpsRepository;
    private final CourseElevationRepository courseElevationRepository;

    private final static ObjectMapper mapper = new ObjectMapper();

    @Bean(name = "courseParseJob")
    public Job courseParseJob() {
        return new JobBuilder("courseParseJob", jobRepository)
                .start(courseParseStep())
                .next(cleanupStep())
                .build();
    }

    @Bean
    public Step courseParseStep() {
        return new StepBuilder("courseParseStep", jobRepository)
                .<GpxInfo, ParsedCourseData>chunk(10, transactionManager)
                .reader(gpxInfoReader())
                .processor(gpxInfoProcessor())
                .writer(parsedDataWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<GpxInfo> gpxInfoReader() {
        return new JdbcCursorItemReaderBuilder<GpxInfo>()
                .name("gpxInfoReader")
                .dataSource(dataSource)
                .sql("SELECT gi.id, gi.gpx, gi.course_detail_id " +
                        "FROM gpx_info gi " +
                        "JOIN course_detail cd ON gi.course_detail_id = cd.id " +
                        "JOIN course c ON c.detail_id = cd.id " +
                        "WHERE gi.status = 'ENABLE' AND c.status = 'ENABLE'")
                .rowMapper(new BeanPropertyRowMapper<>(GpxInfo.class))
                .build();
    }


    @Bean
    public ItemProcessor<GpxInfo, ParsedCourseData> gpxInfoProcessor() {
        return gpxInfo -> {
            JsonNode rootNode = mapper.readTree(gpxInfo.getGpx());
            JsonNode trkpts = rootNode.path("trk").path("trksegs").get(0).path("trkpts");

            List<String> gpsPoints = new ArrayList<>();
            List<String> elevations = new ArrayList<>();

            int count = 0;
            for (JsonNode point : trkpts) {
                count++;
                if (count % 5 == 0) {
                    double lat = point.path("lat").asDouble();
                    double lon = point.path("lon").asDouble();
                    double ele = point.path("ele").asDouble();

                    gpsPoints.add(String.format("[%.6f,%.6f]", lat, lon));
                    elevations.add(String.valueOf(ele));
                }
            }

            CourseDetail courseDetail = courseDetailRepository.getEntityById(gpxInfo.getCourseDetailId());
            Course course = courseRepository.getEntityByCourseDetail(courseDetail);

            return new ParsedCourseData(
                    course,
                    String.join(",", gpsPoints),
                    String.join(",", elevations)
            );
        };
    }

    @Bean
    public ItemWriter<ParsedCourseData> parsedDataWriter() {
        return items -> {
            for (ParsedCourseData data : items) {
                CourseGps courseGps = new CourseGps(data.getGpsPoints(), data.getCourse());
                courseGpsRepository.save(courseGps);

                CourseElevation courseElevation = new CourseElevation(data.getElevations(), data.getCourse());
                courseElevationRepository.save(courseElevation);

                log.info("gps 파싱 성공: {}", data.getCourse().getId());
            }
        };
    }

    @Bean(name = "CourseCleanupStep")
    public Step cleanupStep() {
        return new StepBuilder("cleanupStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("cleanupStep 실행");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }


}
