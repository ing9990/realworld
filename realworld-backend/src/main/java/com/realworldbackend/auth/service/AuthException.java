package com.realworldbackend.auth.service;

import com.realworldbackend.common.exception.BusinessException;
import com.realworldbackend.common.exception.ErrorCode;

public class AuthException extends BusinessException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
