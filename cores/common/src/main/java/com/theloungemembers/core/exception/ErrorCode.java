package com.theloungemembers.core.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "C001", "error.common.bad_request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C002", "error.common.unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "C003", "error.common.forbidden"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "C004", "error.common.not_found"),
    DUPLICATE(HttpStatus.CONFLICT, "C005", "error.common.duplicate"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C006", "error.common.internal_server_error"),

    LOUNGE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "L001", "error.lounge.invalid_input"),
    LOUNGE_NOT_FOUND(HttpStatus.NOT_FOUND, "L002", "error.lounge.not_found"),
    LOUNGE_DUPLICATE_CODE(HttpStatus.CONFLICT, "L003", "error.lounge.duplicate_code");

    private final HttpStatus status;
    private final String code;
    private final String key;

    ErrorCode(HttpStatus status, String code, String key) {
        this.status = status;
        this.code = code;
        this.key = key;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }
}