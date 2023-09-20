package com.realworldbackend.user.domain.service;

import com.realworldbackend.common.exception.ErrorCode;
import com.realworldbackend.common.exception.BusinessException;

public class UsernameDuplicatedException extends BusinessException {

    public UsernameDuplicatedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public UsernameDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
