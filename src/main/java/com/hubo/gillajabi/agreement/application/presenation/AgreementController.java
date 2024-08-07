package com.hubo.gillajabi.agreement.application.presenation;

import com.hubo.gillajabi.agreement.application.dto.request.AgreementRequest;
import com.hubo.gillajabi.agreement.domain.service.AgreementService;
import com.hubo.gillajabi.login.application.annotation.MemberOnly;
import com.hubo.gillajabi.login.application.annotation.UserOnly;
import com.hubo.gillajabi.login.infrastructure.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agreements")
@Tag(name = "Agreement", description = "약관 API")
@RequiredArgsConstructor
public class AgreementController {

    private final AgreementService agreementService;

    @Operation(summary = "약관 동의 업데이트", description = "회원의 약관 동의 상태를 업데이트합니다.")
    @PutMapping("/me")
    @UserOnly
    public ResponseEntity updateAgreements(@RequestBody @Valid AgreementRequest request) {
        String username = SecurityUtil.getCurrentUsername();
        agreementService.updateAgreements(username, request);
        return ResponseEntity.ok().body("약관 동의 여부가 업데이트 되었습니다.");
    }

    @Operation(summary = "약관 조회", description = "약관을 조회합니다")
    @GetMapping("/me")
    @MemberOnly
    public ResponseEntity getAgreements() {
        String username = SecurityUtil.getCurrentUsername();
        agreementService.getAgreements(username);
        return ResponseEntity.ok().body("약관 정보가 전송되었습니다.");
    }
}
