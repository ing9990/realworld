package com.realworldbackend.user.domain;

import com.realworldbackend.common.exception.ErrorCode;
import com.realworldbackend.common.exception.SystemException;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException(ErrorCode errorCode) {
        throw new SystemException(errorCode);
    }
}
