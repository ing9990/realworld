package com.realworldbackend.user.domain;

import com.realworldbackend.common.exception.ErrorCode;
import com.realworldbackend.common.exception.BusinessException;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException(ErrorCode errorCode) {
        throw new BusinessException(errorCode);
    }
}
