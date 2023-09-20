package com.realworldbackend.user.domain;

public enum UserStatus {

    ACTIVE("ACTIVE"),
    DELETED("DELETED");

    final String value;

    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
