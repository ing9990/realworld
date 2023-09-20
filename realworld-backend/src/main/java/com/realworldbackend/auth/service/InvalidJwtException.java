package com.realworldbackend.auth.service;

import com.realworldbackend.common.exception.ErrorCode;
import com.realworldbackend.common.exception.BusinessException;

public class InvalidJwtException extends BusinessException {
    public InvalidJwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
