package com.realworldbackend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Registration
    USERNAME_IS_EMPTY(400, "R001", "Username cannot be empty."),

    // DEFAULT
    INVALID_INPUT(400, "I001", "Invalid input value."),
    METHOD_NOT_ALLOWED(405, "I002", "Method not allowed."),

    INTERNAL_SERVER_ERROR(500, "E500", "Sorry. Something went wrong.");


    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

}
