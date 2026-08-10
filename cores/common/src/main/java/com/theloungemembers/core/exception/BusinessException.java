package com.theloungemembers.core.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1004352911578323463L;

    private final ErrorCodeSpec errorCode;
    private final Object[] args;
    private final String customMessageKey;

    public BusinessException(String customMessageKey) {
        this(CommonErrorCode.INTERNAL_SERVER_ERROR, customMessageKey);
    }

    public BusinessException(ErrorCodeSpec errorCode, String customMessageKey) {
        super(customMessageKey);
        this.errorCode = errorCode;
        this.args = null;
        this.customMessageKey = customMessageKey;
    }

    public BusinessException(ErrorCodeSpec errorCode) {
        super(errorCode.getKey());
        this.errorCode = errorCode;
        this.args = null;
        this.customMessageKey = null;
    }

    public BusinessException(ErrorCodeSpec errorCode, String customMessageKey, Object[] args) {
        super(customMessageKey);
        this.errorCode = errorCode;
        this.args = args;
        this.customMessageKey = customMessageKey;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        this.args = null;
        this.customMessageKey = null;
    }

    public String getMessageKey() {
        return (customMessageKey != null && !customMessageKey.isBlank())
                ? customMessageKey
                : errorCode.getKey();
    }
}