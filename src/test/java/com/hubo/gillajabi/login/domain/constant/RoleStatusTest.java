package com.hubo.gillajabi.login.domain.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleStatusTest {

    @Test
    @DisplayName("Role_USER 로 올바르게 변환한다")
    public void Role_USER_로_올바르게_변환한다() {
        // given
        RoleStatus roleStatus = RoleStatus.USER;

        // when
        String authority = roleStatus.getAuthority();

        // then
        assertEquals("ROLE_USER", authority);
    }

    @Test
    @DisplayName("Role_ADMIN 로 올바르게 변환한다")
    public void Role_ADMIN_로_올바르게_변환한다() {
        // given
        RoleStatus roleStatus = RoleStatus.ADMIN;

        // when
        String authority = roleStatus.getAuthority();

        // then
        assertEquals("ROLE_ADMIN", authority);
    }

    @Test
    @DisplayName("Role_GUEST 로 올바르게 변환한다")
    public void Role_GUEST_로_올바르게_변환한다() {
        // given
        RoleStatus roleStatus = RoleStatus.GUEST;

        // when
        String authority = roleStatus.getAuthority();

        // then
        assertEquals("ROLE_GUEST", authority);
    }

}
