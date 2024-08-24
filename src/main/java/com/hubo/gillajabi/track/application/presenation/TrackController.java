package com.hubo.gillajabi.track.application.presenation;

import com.hubo.gillajabi.login.application.annotation.UserOnly;
import com.hubo.gillajabi.login.infrastructure.util.SecurityUtil;
import com.hubo.gillajabi.track.application.dto.request.TrackEndReqeust;
import com.hubo.gillajabi.track.application.dto.request.TrackStartRequest;
import com.hubo.gillajabi.track.application.dto.request.TrackSendRequest;
import com.hubo.gillajabi.track.application.dto.response.StartTrackResponse;
import com.hubo.gillajabi.track.domain.service.TrackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "트랙 관련 api", description = "트랙 관련 api")
@RequestMapping("/api/track")
public class TrackController {

    private final TrackService trackService;

    @Operation(summary = "트랙 시작 api ", description = "회원의 약관 동의 상태를 업데이트합니다.")
    @UserOnly
    @PostMapping("/start")
    public ResponseEntity startTrack(
            @Valid @RequestBody final TrackStartRequest request) {
        final String username = SecurityUtil.getCurrentUsername();
        final StartTrackResponse response = trackService.startTrack(request, username);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "트랙 재시작 api", description = "트랙을 재시작 합니다 ")
    @UserOnly
    @PostMapping("/restart")
    public ResponseEntity restartTrack() {
        final String username = SecurityUtil.getCurrentUsername();
        trackService.restartTrack(username);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "트랙 종료 api", description = "트랙을 종료/중단 합니다 ")
    @UserOnly
    @PostMapping("/end")
    public ResponseEntity endTrack() {
        final String username = SecurityUtil.getCurrentUsername();
        trackService.endTrack(username);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "트랙 일시정지 api", description = "트랙을 일시정지 합니다 ")
    @UserOnly
    @PostMapping("/pause")
    public ResponseEntity pauseTrack() {
        final String username = SecurityUtil.getCurrentUsername();
        trackService.pauseTrack(username);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "트래킹 데이터 전송 api", description = "트래킹 데이터를 전송합니다 ")
    @UserOnly
    @PostMapping("/send")
    public ResponseEntity sendTrack(
            @Valid @RequestBody final TrackSendRequest request
    ) {
        final String username = SecurityUtil.getCurrentUsername();
        trackService.sendTrack(username, request);

        return ResponseEntity.ok().build();
    }
}
