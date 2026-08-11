package com.theloungemembers.web.common.util;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.theloungemembers.core.exception.BusinessException;
import com.theloungemembers.core.exception.CommonErrorCode;
import com.theloungemembers.web.common.security.WorkerPrincipal;

public class SecurityUtil {

    private SecurityUtil() {}

    /**
     * SecurityContext에서 WorkerPrincipal 추출
     */
    public static Optional<WorkerPrincipal> getCurrentWorker() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        if (authentication.getPrincipal() instanceof WorkerPrincipal customWorker) {
            return Optional.of(customWorker);
        }

        return Optional.empty();
    }

    /**
     * 로그인한 작업자 ID 조회 (없으면 예외 발생)
     */
    public static String getWorkerId() {
        return getCurrentWorker()
                .map(WorkerPrincipal::getWorkerId)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.UNAUTHORIZED));
    }

    /**
     * 로그인한 작업자 이름 조회
     */
    public static String getWorkerName() {
        return getCurrentWorker()
                .map(WorkerPrincipal::getWorkerName)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.UNAUTHORIZED));
    }

    /**
     * 로그인한 작업자 ID 조회 (없을 경우 null 반환)
     */
    public static String getWorkerIdOrNull() {
        return getCurrentWorker()
                .map(WorkerPrincipal::getWorkerId)
                .orElse(null);
    }
}