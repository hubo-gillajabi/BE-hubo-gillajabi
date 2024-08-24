package com.hubo.gillajabi.track.application.presenation;

import com.hubo.gillajabi.image.application.annotation.ImageUploader;
import com.hubo.gillajabi.login.application.annotation.MemberOnly;
import com.hubo.gillajabi.login.application.annotation.UserOnly;
import com.hubo.gillajabi.login.infrastructure.util.SecurityUtil;
import com.hubo.gillajabi.track.application.dto.request.PhotoPointRequest;
import com.hubo.gillajabi.track.domain.service.PhotoPointService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photo-point")
public class PhotoPointController {

    private final PhotoPointService photoPointService;

//    @Operation(summary ="코스 여행 사진 업로드 (포인트) ", description = "해당 포인트에 사진을 업로드합니다.")
//    @PostMapping
//    @ImageUploader
//    @UserOnly
//    public ResponseEntity uploadPhoto(@RequestBody @Valid PhotoPointRequest request){
//        String userName = SecurityUtil.getCurrentUsername();
//        // TODO 위도, 경도, 이미지, 코스
//        photoPointService.uploadPhotoPoint(request, userName);
//
//        return ResponseEntity.ok().build();
//    }

    // TODO redis 에 이미 올라감


    // 사진을 올릴 수 있다.

    // 사진을 삭제 할 수 있다.

}
