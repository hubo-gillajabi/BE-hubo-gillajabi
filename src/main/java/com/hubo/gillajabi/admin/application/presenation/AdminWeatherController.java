package com.hubo.gillajabi.admin.application.presenation;

import com.hubo.gillajabi.login.application.annotation.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/admin/weather")
@Tag(name = "adminWeather", description = "관리자 날씨 API")
public class AdminWeatherController {

    private final JobLauncher jobLauncher;
    private final Job weatherJob;

    public AdminWeatherController(JobLauncher jobLauncher,
                                  @Qualifier("processWeatherJob") Job weatherJob) {
        this.jobLauncher = jobLauncher;
        this.weatherJob = weatherJob;
    }

    @Operation(summary = "날씨 정보 정리", description = "날씨 정보 요약을 수행합니다.")
    @PostMapping("/update")
    @AdminOnly
    public ResponseEntity<String> runWeatherUpdate() {
        try {
            JobParameters parameters = new JobParametersBuilder()
                    .addDate("date", new Date())
                    .toJobParameters();
            jobLauncher.run(weatherJob, parameters);
            return ResponseEntity.ok("날씨 정보 성공적으로 요약되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("날씨 정보 정리 실패: " + e.getMessage());
        }
    }

}
