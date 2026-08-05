package com.theloungemembers.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 외부에서 new 직접 생성 방지
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 JSON 결과에서 제외
public class ApiResponse<T> {

    private final boolean success;
    private final T data;
    private final ErrorResponse error;

    // 성공 응답 정의
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static ApiResponse<Void> ok() {
        return new ApiResponse<>(true, null, null);
    }

    // 실패 응답 정의
    public static ApiResponse<Void> fail(String code, String message) {
        return new ApiResponse<>(false, null, new ErrorResponse(code, message));
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        private final String code;
        private final String message;
    }
}