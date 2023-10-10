package com.realworldbackend.domain.user;

import com.realworldbackend.application.exception.ErrorCode;
import com.realworldbackend.application.exception.BusinessException;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException(ErrorCode errorCode) {
        throw new BusinessException(errorCode);
    }
}
