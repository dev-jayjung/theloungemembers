package com.theloungemembers.core.exception;

import java.util.Locale;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.MessageHelper;
import com.theloungemembers.core.util.ResponseUtil;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageHelper messageHelper;

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResponse<Void>> handleBusinessException(final BusinessException e, Locale locale) {
        ErrorCode errorCode = e.getErrorCode();

        String message = messageHelper.getMessage(errorCode.getKey(), e.getArgs());

        return ResponseUtil.fail(errorCode, message);
    }
}