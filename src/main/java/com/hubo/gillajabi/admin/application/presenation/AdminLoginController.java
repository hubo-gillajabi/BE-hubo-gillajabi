package com.hubo.gillajabi.admin.application.presenation;

import com.hubo.gillajabi.admin.application.dto.request.LoginRequest;
import com.hubo.gillajabi.admin.domain.service.AdminLoginService;
import com.hubo.gillajabi.login.application.dto.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/login")
@RequiredArgsConstructor
@Tag(name = "adminLogin", description = "관리자 로그인 API")
public class AdminLoginController {

    private final AdminLoginService adminLoginService;

    @PostMapping
    @Operation(summary = "관리자 로그인", description = "관리자 로그인")
    public ResponseEntity<TokenResponse> login(
            @RequestBody LoginRequest loginRequest) {

        TokenResponse tokenResponse = adminLoginService.login(loginRequest);

        return ResponseEntity.ok(tokenResponse);
    }

}

