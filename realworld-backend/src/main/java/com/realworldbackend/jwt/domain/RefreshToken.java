package com.realworldbackend.jwt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class RefreshToken {

    @Id
    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "auth_user_id", unique = true, nullable = false)
    private Long userId;

    protected RefreshToken() {
    }

    public RefreshToken(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

}
