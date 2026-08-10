package com.theloungemembers.core.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.exception.ErrorCodeSpec;

public class ResponseUtil {

    private ResponseUtil() {}

    // 성공 응답 (데이터 포함)
    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    // 성공 응답 (데이터 없음 - Void)
    public static ResponseEntity<ApiResponse<Void>> success() {
        return ResponseEntity.ok(ApiResponse.ok());
    }

    // 성공 응답 (HTTP 상태 코드 커스텀)
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, HttpStatus status) {
        return ResponseEntity.status(status).body(ApiResponse.ok(data));
    }

    // 실패 응답 (ErrorCode 기반)
    public static ResponseEntity<ApiResponse<Void>> fail(ErrorCodeSpec errorCode) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode.getCode(), errorCode.getKey()));
    }

    // 실패 응답 (메시지 커스텀)
    public static ResponseEntity<ApiResponse<Void>> fail(ErrorCodeSpec errorCode, String customMessage) {
        return ResponseEntity.status(errorCode.getStatus()).body(ApiResponse.fail(errorCode.getCode(), customMessage));
    }
}