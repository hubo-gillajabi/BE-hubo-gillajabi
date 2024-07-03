package com.hubo.gillajabi.login.infrastructure.aop;

import com.hubo.gillajabi.login.infrastructure.exception.AuthException;
import com.hubo.gillajabi.login.infrastructure.exception.AuthExceptionCode;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MemberOnlyAspect {

    @Before("@annotation(com.hubo.gillajabi.login.application.annotation.MemberOnly)")
    public void checkMemberOnlyAccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !hasRequiredRole(authentication)) {
            throw new AuthException(AuthExceptionCode.ACCESS_DENIED);
        }
    }

    private boolean hasRequiredRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority ->
                        "ROLE_USER".equals(grantedAuthority.getAuthority()) ||
                                "ROLE_ADMIN".equals(grantedAuthority.getAuthority()) ||
                                "ROLE_GUEST".equals(grantedAuthority.getAuthority()));
    }
}
