package com.realworldbackend.user.domain.service;

import com.realworldbackend.common.exception.BusinessException;
import com.realworldbackend.common.exception.ErrorCode;

public class PasswordMisMatchException extends BusinessException {

    public PasswordMisMatchException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public PasswordMisMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
