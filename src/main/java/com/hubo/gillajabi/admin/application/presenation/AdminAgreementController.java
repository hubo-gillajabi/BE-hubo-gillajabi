package com.hubo.gillajabi.admin.application.presenation;


import com.hubo.gillajabi.login.application.annotation.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/agreements")
@RequiredArgsConstructor
@Tag(name = "AdminAgreement", description = "관리자 약관 API")
public class AdminAgreementController {

    @Operation(summary = "약관 개정 안내", description = "약관 개정 안내를 생성합니다.")
    @PostMapping
    @AdminOnly
    public void inturduceAgreement() {
        // TODO
        // 약관 개정 메일 발송
    }

    @Operation(summary = "약관 생성 안내", description = "신규 약관을 생성합니다")
    @PostMapping("/new")
    @AdminOnly
    public void createAgreement() {
        // TODO
        // 약관 생성 비공개로
        // 미리 약관 배치 잡
    }

    @Operation(summary = "회원 메일 안내 전송", description = "회원에게 약관 메일을 전송합니다.")
    @PostMapping("/send")
    @AdminOnly
    public void sendAgreementMail() {
        // TODO
        // 약관 메일 전송
    }
}
