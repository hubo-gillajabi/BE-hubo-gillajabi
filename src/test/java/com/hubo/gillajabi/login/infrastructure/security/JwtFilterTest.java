package com.hubo.gillajabi.login.infrastructure.security;

import com.hubo.gillajabi.login.domain.constant.CustomUserDetails;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class JwtFilterTest {

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private JwtFilter jwtFilter;

    @Test
    @DisplayName("유효한 토큰으로 인증 성공 테스트")
    void 유효한_토큰으로_인증_성공_테스트() throws Exception {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer 유효한토큰");
        CustomUserDetails userDetails = new CustomUserDetails("user", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")).toString());

        when(tokenProvider.validateToken("유효한토큰")).thenReturn(true);
        when(tokenProvider.getAuthentication("유효한토큰")).thenReturn(userDetails);

        // When
        jwtFilter.doFilterInternal(request, response, (req, res) -> {});

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isEqualTo(userDetails);
    }

    @Test
    @DisplayName("유효하지 않은 토큰으로 인증 실패 테스트")
    void 유효하지_않은_토큰으로_인증_실패_테스트() throws Exception {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer 유효하지않은토큰");

        when(tokenProvider.validateToken("유효하지않은토큰")).thenReturn(false);

        // When
        jwtFilter.doFilterInternal(request, response, (req, res) -> {});

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("토큰 없이 요청 테스트")
    void 토큰_없이_요청_테스트() throws Exception {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // When
        jwtFilter.doFilterInternal(request, response, (req, res) -> {});

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

}
