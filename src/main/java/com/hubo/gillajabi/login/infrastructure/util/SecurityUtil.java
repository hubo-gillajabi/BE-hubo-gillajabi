package com.hubo.gillajabi.login.infrastructure.util;

import com.hubo.gillajabi.login.domain.constant.CustomUserDetails;
import com.hubo.gillajabi.login.infrastructure.exception.AuthException;
import com.hubo.gillajabi.login.infrastructure.exception.AuthExceptionCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static CustomUserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    public static String getCurrentUsername() {
        CustomUserDetails userDetails = getCurrentUserDetails();
        if (userDetails == null) {
            throw new AuthException(AuthExceptionCode.ACCESS_DENIED);
        }
        return userDetails.getUsername();
    }
}