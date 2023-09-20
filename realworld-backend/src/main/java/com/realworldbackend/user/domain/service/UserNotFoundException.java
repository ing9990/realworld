package com.realworldbackend.user.domain.service;

import com.realworldbackend.common.exception.ErrorCode;
import com.realworldbackend.common.exception.BusinessException;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(ErrorCode errorCode) {
        throw new BusinessException(errorCode);
    }
}
