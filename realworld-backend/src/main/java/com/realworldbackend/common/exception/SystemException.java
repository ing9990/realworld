package com.realworldbackend.common.exception;

import lombok.Getter;

@Getter
public class SystemException extends RuntimeException {

    private ErrorCode errorCode;

    public SystemException(String message, ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    public SystemException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
