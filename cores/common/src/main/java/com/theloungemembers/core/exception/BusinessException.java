package com.theloungemembers.core.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1004352911578323463L;

    private final ErrorCode errorCode;
    private final Object[] args;

    public BusinessException(String message) {
        this(message, ErrorCode.INTERNAL_SERVER_ERROR);
    }

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.args = null;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getKey());
        this.errorCode = errorCode;
        this.args = null;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        this.args = null;
    }
}