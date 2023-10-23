package com.realworldbackend.application.security;

import lombok.Getter;

@Getter
public class UserPayload {

    private Long userId;

    public UserPayload(Long userId) {
        this.userId = userId;
    }

    protected UserPayload() {
    }
}
