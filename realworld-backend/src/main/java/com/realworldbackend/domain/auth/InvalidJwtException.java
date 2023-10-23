package com.realworldbackend.domain.auth;

import com.realworldbackend.application.exception.ErrorCode;

public class InvalidJwtException extends AuthException {
    public InvalidJwtException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidJwtException() {
        super(ErrorCode.INVALID_ACCESS_TOKEN);
    }
}
