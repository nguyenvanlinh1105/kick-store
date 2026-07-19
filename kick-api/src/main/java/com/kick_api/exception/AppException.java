package com.kick_api.exception;

import com.kick_api.constant.ErrorCode;

public class AppException extends RuntimeException{
    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode){
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }

    public AppException(ErrorCode errorCode, String customMessage){
        super(customMessage);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
