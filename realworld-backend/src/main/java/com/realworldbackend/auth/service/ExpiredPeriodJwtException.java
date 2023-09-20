package com.realworldbackend.auth.service;

import com.realworldbackend.common.exception.ErrorCode;
import com.realworldbackend.common.exception.BusinessException;

public class ExpiredPeriodJwtException extends BusinessException {
    public ExpiredPeriodJwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
