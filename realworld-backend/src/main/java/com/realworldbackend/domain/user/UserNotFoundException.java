package com.realworldbackend.domain.user;

import com.realworldbackend.application.exception.ErrorCode;
import com.realworldbackend.application.exception.BusinessException;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        this(ErrorCode.NOT_FOUND_USER);
    }

    public UserNotFoundException(ErrorCode errorCode) {
        throw new BusinessException(errorCode);
    }
}
