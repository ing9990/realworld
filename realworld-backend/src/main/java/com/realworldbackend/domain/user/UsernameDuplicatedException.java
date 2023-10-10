package com.realworldbackend.domain.user;

import com.realworldbackend.application.exception.ErrorCode;
import com.realworldbackend.application.exception.BusinessException;

public class UsernameDuplicatedException extends BusinessException {

    public UsernameDuplicatedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public UsernameDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UsernameDuplicatedException() {
        this(ErrorCode.DUPLICATED_USERNAME);
    }
}
