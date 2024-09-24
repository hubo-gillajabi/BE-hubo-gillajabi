package com.hubo.gillajabi.point.application.presenation;

import com.hubo.gillajabi.image.application.annotation.ImageUploader;
import com.hubo.gillajabi.login.application.annotation.UserOnly;
import com.hubo.gillajabi.login.infrastructure.util.SecurityUtil;
import com.hubo.gillajabi.point.application.dto.request.UserPointRequest;
import com.hubo.gillajabi.point.domain.service.UserPointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user-point")
@Tag(name = "사용자 포인트", description = "사용자 포인트를 관리하는 API")
public class UserPointController {

    private final UserPointService userPointService;

    @Operation(summary = "사용자 포인트 저장", description = "사용자의 포인트를 저장합니다.")
    @PostMapping
    @UserOnly
    @ImageUploader
    public ResponseEntity saveUserPoint(@Valid @RequestBody final UserPointRequest request) {
        final String username = SecurityUtil.getCurrentUsername();
        userPointService.saveUserPoint(username, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "사용자 포인트 삭제", description = "사용자의 포인트를 삭제합니다.")
    @DeleteMapping(("/{id}"))
    @UserOnly
    public ResponseEntity deleteUserPoint(@PathVariable final Long id) {
        final String username = SecurityUtil.getCurrentUsername();
        userPointService.deleteUserPoint(username, id);

        return ResponseEntity.noContent().build();
    }
}
