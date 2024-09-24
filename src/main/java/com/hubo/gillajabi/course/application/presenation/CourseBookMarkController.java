package com.hubo.gillajabi.course.application.presenation;

import com.hubo.gillajabi.course.domain.service.CourseBookMarkService;
import com.hubo.gillajabi.login.application.annotation.UserOnly;
import com.hubo.gillajabi.login.infrastructure.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/api/course/bookmark")
@Tag(name = "CourseBookmark", description = "코스 북마크 관련 API")
@RequiredArgsConstructor
public class CourseBookMarkController {

    private final CourseBookMarkService courseBookMarkService;

    // TODO: 임시
    @Operation(summary = "코스 북마크 토글", description = "북마크를 추가하거나 삭제합니다.")
    @PostMapping("/{id}")
    @UserOnly
    public ResponseEntity toggleCourseBookmark(@PathVariable Long id) {
        String username = SecurityUtil.getCurrentUsername();
        courseBookMarkService.toggleCourseBookmark(id, username);
        return ResponseEntity.ok().body("북마크를 추가했습니다.");
    }

    @Operation(summary = "코스 북마크 해제", description =  "북마크를 제거 합니다")
    @DeleteMapping("/{id}")
    @UserOnly
    public ResponseEntity deleteCourseBookmark(@PathVariable Long id){
        String username = SecurityUtil.getCurrentUsername();
        courseBookMarkService.deleteCourseBookmark(id, username);
        return new ResponseEntity(noContent().build().getStatusCode());
    }
}