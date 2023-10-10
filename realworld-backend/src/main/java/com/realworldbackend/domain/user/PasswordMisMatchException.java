package com.realworldbackend.domain.user;

import com.realworldbackend.application.exception.BusinessException;
import com.realworldbackend.application.exception.ErrorCode;

public class PasswordMisMatchException extends BusinessException {

    public PasswordMisMatchException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public PasswordMisMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
