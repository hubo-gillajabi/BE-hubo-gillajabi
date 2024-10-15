package com.hubo.gillajabi.admin.application.presenation;

import com.hubo.gillajabi.admin.application.dto.request.CityImageRequest;
import com.hubo.gillajabi.admin.domain.service.AdminCityService;
import com.hubo.gillajabi.image.application.annotation.ImageUploader;
import com.hubo.gillajabi.login.application.annotation.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/cities")
@RequiredArgsConstructor
@Tag(name = "AdminCity", description = "관리자 도시 API")
public class AdminCityController {

    private final AdminCityService adminCityService;

    @Operation(summary = "도시 이미지 추가", description = "도시를 이미지를 추가합니다.")
    @AdminOnly
    @PostMapping
    @ImageUploader
    public ResponseEntity addCityImage(@Valid @RequestBody CityImageRequest request) {
        adminCityService.addCityImages(request);
        return ResponseEntity.ok("이미지가 추가되었습니다.");
    }

    @Operation(summary = "도시 이미지 삭제", description = "도시 이미지를 삭제합니다.")
    @AdminOnly
    @DeleteMapping("{id}")
    public ResponseEntity deleteCityImage(@PathVariable Long id) {
        adminCityService.deleteCityImage(id);
        return ResponseEntity.ok("이미지가 삭제되었습니다.");
    }
}
