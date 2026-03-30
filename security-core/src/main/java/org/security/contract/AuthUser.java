package org.security.contract;

import java.util.List;

public class AuthUser {

    private Long userId;
    private String username;
    private String password;
    private List<String> roles;

    public AuthUser(
            Long userId,
            String username,
            String password,
            List<String> roles
    ) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }
}