package com.grankhan.loan_service.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Long id;
    private String email;
    private String passwordHash;
    private UserRole role;

    public User(Long id, String email, String passwordHash, UserRole role) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public boolean isAdmin() {
        return this.role == UserRole.ADMIN;
    }
}
