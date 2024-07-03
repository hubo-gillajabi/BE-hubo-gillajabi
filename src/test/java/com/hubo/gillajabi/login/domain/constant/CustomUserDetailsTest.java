package com.hubo.gillajabi.login.domain.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CustomUserDetailsTest {

    @Test
    @DisplayName("CustomUserDetails 생성자 - 사용자 이름 테스트")
    void 사용자_이름_테스트() {
        // given
        String username = "testuser";
        String role = "ROLE_USER";

        // when
        CustomUserDetails userDetails = new CustomUserDetails(username, role);

        // then
        assertThat(userDetails.getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName("CustomUserDetails 생성자 - 권한 테스트")
    void 권한_테스트() {
        // given
        String role = "ROLE_USER";

        // when
        CustomUserDetails userDetails = new CustomUserDetails("testuser", role);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // then
        assertThat(authorities.iterator().next()).isInstanceOf(SimpleGrantedAuthority.class);
        assertThat(authorities.iterator().next().getAuthority()).isEqualTo(role);
    }

    @DisplayName("계정 활성화 상태 - 계정 만료 여부 테스트")
    void 계정_만료_여부_테스트() {
        // given
        CustomUserDetails userDetails = new CustomUserDetails("testuser", "ROLE_USER");

        // then
        assertThat(userDetails.isAccountNonExpired()).isTrue();
    }

    @Test
    @DisplayName("계정 활성화 상태 - 계정 잠금 여부 테스트")
    void 계정_잠금_여부_테스트() {
        // given
        CustomUserDetails userDetails = new CustomUserDetails("testuser", "ROLE_USER");

        // then
        assertThat(userDetails.isAccountNonLocked()).isTrue();
    }

    @Test
    @DisplayName("계정 활성화 상태 - 자격 증명 만료 여부 테스트")
    void 자격_증명_만료_여부_테스트() {
        // given
        CustomUserDetails userDetails = new CustomUserDetails("testuser", "ROLE_USER");

        // then
        assertThat(userDetails.isCredentialsNonExpired()).isTrue();
    }

    @Test
    @DisplayName("계정 활성화 상태 - 활성화 여부 테스트")
    void 활성화_여부_테스트() {
        // given
        CustomUserDetails userDetails = new CustomUserDetails("testuser", "ROLE_USER");

        // then
        assertThat(userDetails.isEnabled()).isTrue();
    }

}
