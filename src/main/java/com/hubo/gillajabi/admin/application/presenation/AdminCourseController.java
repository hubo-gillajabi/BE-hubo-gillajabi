package com.hubo.gillajabi.admin.application.presenation;


import com.hubo.gillajabi.admin.application.dto.request.CourseImageRequest;
import com.hubo.gillajabi.admin.domain.service.AdminCourseService;
import com.hubo.gillajabi.image.application.annotation.ImageUploader;
import com.hubo.gillajabi.login.application.annotation.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/courses")
@RequiredArgsConstructor
@Tag(name = "AdminCourse", description = "관리자 코스 API")
public class AdminCourseController {

    private final AdminCourseService adminCourseService;

    @Operation(summary = "코스 이미지 추가", description = "코스 이미지를 추가합니다.")
    @PostMapping
    @AdminOnly
    @ImageUploader
    public ResponseEntity<String> addCourseImage(@Valid @RequestBody CourseImageRequest request) {
        adminCourseService.addCourseImages(request);
        return ResponseEntity.ok("이미지가 추가되었습니다.");
    }

    @Operation(summary = "코스 이미지 삭제", description = "코스 이미지를 삭제합니다")
    @DeleteMapping("/{id}")
    @AdminOnly
    public ResponseEntity<String> deleteCourseImage(@PathVariable Long id) {
        adminCourseService.deleteCourseImage(id);
        return ResponseEntity.ok("이미지가 삭제되었습니다.");
    }
}
