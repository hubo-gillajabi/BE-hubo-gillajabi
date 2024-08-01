package com.hubo.gillajabi.login.infrastructure.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SwaggerAccessFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // "/swagger-ui/**" 경로에만 필터 적용
        if (new AntPathRequestMatcher("/swagger-ui/**").matches(request)) {
            Boolean swaggerAccess = (Boolean) request.getSession().getAttribute("swaggerAccess");

            if (swaggerAccess == null || !swaggerAccess) {
                // 세션에 플래그가 없거나 false인 경우 접근 차단
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
