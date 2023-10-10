package com.realworldbackend.domain.auth;

import com.realworldbackend.application.exception.BusinessException;
import com.realworldbackend.application.exception.ErrorCode;

public class AuthException extends BusinessException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
