package com.hubo.gillajabi.admin.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderConfigTest {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("passwordEncoder 인코딩 테스트")
    public void password_인코딩_테스트() {
        // given
        String originalPassword = "hello world!";

        // when
        String encodedPassword = passwordEncoder.encode(originalPassword);
        System.out.println("인코딩된 비밀번호: " + encodedPassword);

        // then
        assertTrue(passwordEncoder.matches(originalPassword, encodedPassword), "인코딩된 비밀번호가 원본과 일치해야 합니다.");
    }
}
