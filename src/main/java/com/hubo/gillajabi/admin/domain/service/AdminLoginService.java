package com.hubo.gillajabi.admin.domain.service;

import com.hubo.gillajabi.admin.application.dto.request.LoginRequest;
import com.hubo.gillajabi.admin.domain.entity.Admin;
import com.hubo.gillajabi.admin.infrastructure.exception.AdminLoginException;
import com.hubo.gillajabi.admin.infrastructure.exception.AdminLoginExceptionCode;
import com.hubo.gillajabi.admin.infrastructure.presistence.AdminRepository;
import com.hubo.gillajabi.login.application.dto.response.TokenResponse;
import com.hubo.gillajabi.login.domain.entity.MemberAuthentication;
import com.hubo.gillajabi.login.infrastructure.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminLoginService {

    private final AdminRepository adminRepository;
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public TokenResponse login(LoginRequest loginRequest) {
        Admin admin = adminRepository.findById(loginRequest.id())
                        .orElseThrow(() -> new AdminLoginException(AdminLoginExceptionCode.ADMIN_NOT_FOUND));

        if (!bCryptPasswordEncoder.matches(loginRequest.password(), admin.getPassword())) {
            throw new AdminLoginException(AdminLoginExceptionCode.INVALID_PASSWORD);
        }

        MemberAuthentication authentication = admin.getMemberAuthentication();

        return tokenProvider.createToken(authentication);
    }
}
