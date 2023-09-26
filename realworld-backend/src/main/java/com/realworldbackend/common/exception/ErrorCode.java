package com.realworldbackend.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Registration
    DUPLICATED_USERNAME(400, "R002", "Username is already taken."),
    DUPLICATED_EMAIL(400, "R003", "Email is already taken."),
    INVALID_EMAIL(400, "R004", "Invalid email format."),

    // AUTH
    EXPIRED_PERIOD_REFRESH_TOKEN(401, "A101", "Refresh token has expired."),
    INVALID_REFRESH_TOKEN(401, "A102", "Invalid refresh token."),
    EXPIRED_PERIOD_ACCESS_TOKEN(401, "A103", "Access token has expired."),
    INVALID_ACCESS_TOKEN(401, "A104", "Invalid access token."),
    FAIL_TO_VALIDATE_TOKEN(401, "A105", "Fail to validate token."),
    EMAIL_MISMATCH(401, "A106", "Email is not found"),
    PASSWORD_MISMATCH(401, "A107", "Password mismatch"),

    // DEFAULT
    INVALID_INPUT(400, "I001", "Invalid input value."),
    METHOD_NOT_ALLOWED(405, "I002", "Method not allowed."),

    INTERNAL_SERVER_ERROR(500, "E500", "Sorry. Something went wrong."),

    // RESOURCES_NOT_FOUND
    NOT_FOUND_USER(404, "N001", "User not found."),
    NOT_FOUND_USER_FOLLOW(404, "N002", "Cannot find the user to follow."),

    NOT_SUPPORTED_OAUTH_SERVICE(401, "O001", "OAuth services isn't supported"),

    ARTICLE_NOT_FOUND(404, "ANF001", "Article not found.");

    private int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
