package com.hubo.gillajabi.image.application.presenation;

import com.hubo.gillajabi.image.application.dto.response.ImageUrlResponse;
import com.hubo.gillajabi.image.domain.service.ImageUploadService;
import com.hubo.gillajabi.image.infrastructure.exception.ImageException;
import com.hubo.gillajabi.image.infrastructure.exception.ImageExceptionCode;
import com.hubo.gillajabi.login.application.annotation.UserOnly;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageUploadService imageUploadService;


    @Operation(summary = "이미지 업로드")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @UserOnly
    public ResponseEntity<ImageUrlResponse> uploadImage(@RequestPart("file") @NotNull MultipartFile file) {
        ImageUrlResponse response= imageUploadService.uploadImage(file);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "이미지 url 삭제", description = "단 이미 올라간 생성된 url은 삭제 되지 않습니다, 개별 도메인 삭제 필요")
    @DeleteMapping("/delete")
    @UserOnly
    public ResponseEntity<String> deleteImage(@RequestParam @NotEmpty String imageUrl) {
        imageUploadService.deleteImage(imageUrl);

        return ResponseEntity.ok("이미지 삭제 성공");
    }

}
