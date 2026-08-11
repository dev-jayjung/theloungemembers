package com.theloungemembers.core.exception;

import org.springframework.http.HttpStatus;

public enum CommonErrorCode implements ErrorCodeSpec {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "C001", "error.common.bad_request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C002", "error.common.unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "C003", "error.common.forbidden"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "C004", "error.common.not_found"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C005", "error.common.method_not_allowed"),
    DUPLICATE(HttpStatus.CONFLICT, "C006", "error.common.duplicate"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C007", "error.common.internal_server_error");

    private final HttpStatus status;
    private final String code;
    private final String key;

    CommonErrorCode(HttpStatus status, String code, String key) {
        this.status = status;
        this.code = code;
        this.key = key;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getKey() {
        return key;
    }
}