//package com.hubo.gillajabi.course.infrastructure.job;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
//import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseTagRepository;
//import com.hubo.gillajabi.crawl.infrastructure.persistence.TagRepository;
//import com.hubo.gillajabi.review.infrastructure.persistence.PostTagRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
//public class CourseTagJobConfig {
//
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//
//    private final DataSource dataSource;
//
//    private final CourseRepository courseRepository;
//
//    private final CourseTagRepository courseTagRepository;
//
//    private final PostTagRepository postTagRepository;
//
//    private final TagRepository tagRepository;
//
//    private final static ObjectMapper mapper = new ObjectMapper();
//
//    @Bean(name = "courseTagJob")
//    public Job courseTagJob() {
//        return new JobBuilder("courseTagJob", jobRepository)
//                .start(courseTagStep())
//                .next(cleanupStep())
//                .build();
//    }
//
//    @Bean
//    public Step courseTagStep() {
//        return null;
//    }
////        return new StepBuilder("courseTagStep")
//
//    @Bean
//    public Step cleanupStep() {
//        return null;
//    }
//}
// TODO 배치잡 구현