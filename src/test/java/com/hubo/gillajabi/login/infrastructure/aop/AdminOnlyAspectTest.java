package com.hubo.gillajabi.login.infrastructure.aop;

import com.hubo.gillajabi.login.infrastructure.exception.AuthException;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AdminOnlyAspectTest {

    @InjectMocks
    private AdminOnlyAspect adminOnlyAspect;

    @Test
    @DisplayName("관리자 권한이 있을 때 접근 테스트")
    void 관리자_권한이_있을_때_접근_테스트() {
        // given
        var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        var authentication = new UsernamePasswordAuthenticationToken("user", null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // when & then
        adminOnlyAspect.checkMemberOnlyAccess();
    }

    @Test
    @DisplayName("관리자 권한이 없을 때 접근 예외 테스트")
    void 관리자_권한이_없을_때_접근_예외_테스트() {
        // given
        var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        var authentication = new UsernamePasswordAuthenticationToken("user", null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // when & then
        assertThrows(AuthException.class, () -> adminOnlyAspect.checkMemberOnlyAccess());
    }

    @Test
    @DisplayName("인증 정보가 없을 때 접근 예외 테스트")
    void 인증_정보가_없을_때_접근_예외_테스트() {
        // given
        SecurityContextHolder.clearContext();

        // when & then
        assertThrows(AuthException.class, () -> adminOnlyAspect.checkMemberOnlyAccess());
    }
}
