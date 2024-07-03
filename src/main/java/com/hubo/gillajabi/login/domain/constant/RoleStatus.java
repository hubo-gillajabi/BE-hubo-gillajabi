package com.hubo.gillajabi.login.domain.constant;

public enum RoleStatus {
    USER, ADMIN, GUEST;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
