package com.hubo.gillajabi.login.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class SwaggerAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.equals("/swagger")) {
            String loginPageUrl = "/admin/login";
            String redirectUrl = "/admin" + requestURI;
            String encodedRedirectUrl = URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8);
            response.sendRedirect(loginPageUrl + "?redirect=" + encodedRedirectUrl);
        } else {
            // 다른 URI에 대한 처리를 여기에 추가할 수 있습니다.
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
}