package com.theloungemembers.core.exception;

import java.util.Locale;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.MessageHelper;
import com.theloungemembers.core.util.ResponseUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageHelper messageHelper;

    /**
     * 커스텀 비즈니스 예외 (BusinessException)
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResponse<Void>> handleBusinessException(final BusinessException e, Locale locale) {
        logException(e, e.getErrorCode().toString(), false);

        return ResponseUtil.fail(e.getErrorCode(), messageHelper.getMessage(e.getMessageKey(), e.getArgs()));
    }

    /**
     * @Valid, @Validated 파라미터 검증 예외 (MethodArgumentNotValidException)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e) {
        logException(e, CommonErrorCode.BAD_REQUEST.toString(), false);

        // 첫 번째 검증 실패 필드의 에러 메시지 추출
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(fieldError -> String.format("[%s] %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .orElse(messageHelper.getMessage(CommonErrorCode.BAD_REQUEST));

        return ResponseUtil.fail(CommonErrorCode.BAD_REQUEST, errorMessage);
    }

    /**
     * 지원하지 않는 HTTP Method 호출 예외 (예: POST endpoint에 GET 요청)
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupportedException(
            final HttpRequestMethodNotSupportedException e) {
        logException(e, CommonErrorCode.METHOD_NOT_ALLOWED.toString(), false);

        return ResponseUtil.fail(CommonErrorCode.METHOD_NOT_ALLOWED,
                messageHelper.getMessage(CommonErrorCode.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(final NoResourceFoundException e) {
        logException(e, CommonErrorCode.NOT_FOUND.toString(), false);

        return ResponseUtil.fail(CommonErrorCode.NOT_FOUND,
                messageHelper.getMessage(CommonErrorCode.NOT_FOUND));
    }

    /**
     * 최상위 예외 (처리되지 않은 모든 런타임/시스템 예외)
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<Void>> handleException(final Exception e) {
        logException(e, CommonErrorCode.INTERNAL_SERVER_ERROR.toString(), true);

        return ResponseUtil.fail(CommonErrorCode.INTERNAL_SERVER_ERROR,
                messageHelper.getMessage(CommonErrorCode.INTERNAL_SERVER_ERROR));
    }

    /**
     * 예외 발생 위치 공통 로깅 메서드
     */
    private void logException(Exception ex, String code, boolean isError) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        StackTraceElement element = (stackTrace != null && stackTrace.length > 0) ? stackTrace[0] : null;

        String location = (element != null)
                ? String.format("%s.%s(%s:%d)", element.getClassName(), element.getMethodName(),
                        element.getFileName(), element.getLineNumber()) : "Unknown Location";

        if (isError) {
            // ex를 마지막에 넣으면 전체 스택트레이스도 출력됨
            log.error("Exception occurred: [{}] {} - {}", code, location, ex.getMessage(), ex);
        } else {
            log.warn("Exception occurred: [{}] {} - {}", code, location, ex.getMessage());
        }
    }
}