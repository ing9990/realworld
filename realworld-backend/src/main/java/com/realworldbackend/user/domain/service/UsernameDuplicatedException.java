package com.realworldbackend.user.domain.service;

import com.realworldbackend.common.exception.ErrorCode;
import com.realworldbackend.common.exception.SystemException;

public class UsernameDuplicatedException extends RuntimeException {

    public UsernameDuplicatedException(ErrorCode errorCode) {
        throw new SystemException(errorCode);
    }
}
