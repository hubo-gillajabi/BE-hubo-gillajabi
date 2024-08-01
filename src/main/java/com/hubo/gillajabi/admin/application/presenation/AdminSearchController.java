package com.hubo.gillajabi.admin.application.presenation;


import com.hubo.gillajabi.login.application.annotation.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

//TODO
@RestController
@RequestMapping("/api/admin/search")
@RequiredArgsConstructor
@Tag(name = "adminSearch", description = "관리자 검색 API")
public class AdminSearchController {

    private final Job courseSearchJob;
    private final JobLauncher jobLauncher;

    @Operation(summary = "검색 인덱싱", description = "검색 인덱싱을 수행합니다.")
    @PostMapping("/indexing")
    @AdminOnly
    public ResponseEntity<String> runSearchIndexing() {
        try {
            JobParameters parameters = new JobParametersBuilder()
                    .addDate("date", new Date())
                    .toJobParameters();
            jobLauncher.run(courseSearchJob, parameters);
            return ResponseEntity.ok("검색 인덱싱이 성공적으로 시작되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("검색 인덱싱 시작 실패: " + e.getMessage());
        }
    }

}
