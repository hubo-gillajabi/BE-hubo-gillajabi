package com.hubo.gillajabi.admin.application.presenation;


import com.hubo.gillajabi.admin.domain.service.AdminCourseParseService;
import com.hubo.gillajabi.login.application.annotation.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/courses/parse")
@RequiredArgsConstructor
@Tag(name = "AdminCourseParse", description = "관리자 코스 파싱 API")
public class AdminCourseParseController {

    private final AdminCourseParseService adminCourseParseService;

    @Operation(summary = "코스 파싱", description = "코스를 파싱합니다.")
    @PostMapping()
    @AdminOnly
    public ResponseEntity parseCourse() {
        try{
            adminCourseParseService.startCourseParseJob();
        } catch (Exception e) {
            throw new RuntimeException("코스 파싱 실패", e);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
