package com.realworldbackend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Registration
    DUPLICATED_USERNAME(400, "R002", "Username is already taken."),
    DUPLICATED_EMAIl(400, "R003", "Email is already taken."),
    INVALID_EMAIL(400, "R004", "Invalid email format."),


    // DEFAULT
    INVALID_INPUT(400, "I001", "Invalid input value."),

    METHOD_NOT_ALLOWED(405, "I002", "Method not allowed."),

    INTERNAL_SERVER_ERROR(500, "E500", "Sorry. Something went wrong.");


    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

}
