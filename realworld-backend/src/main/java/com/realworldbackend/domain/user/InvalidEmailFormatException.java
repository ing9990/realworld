package com.realworldbackend.domain.user;

import com.realworldbackend.application.exception.ErrorCode;
import com.realworldbackend.application.exception.BusinessException;

public class InvalidEmailFormatException extends BusinessException {

    public InvalidEmailFormatException(ErrorCode errorCode) {
        super(errorCode);
    }
}
