package com.theloungemembers.core.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCodeSpec {
    HttpStatus getStatus();
    String getCode();
    String getKey();
}