package com.realworldbackend.domain.auth;

import com.realworldbackend.application.exception.ErrorCode;
import com.realworldbackend.application.exception.BusinessException;

public class InvalidJwtException extends BusinessException {
    public InvalidJwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
