package com.realworldbackend.domain.auth;

import com.realworldbackend.application.exception.ErrorCode;
import com.realworldbackend.application.exception.BusinessException;

public class ExpiredPeriodJwtException extends BusinessException {
    public ExpiredPeriodJwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
