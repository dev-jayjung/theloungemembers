package com.theloungemembers.core.common.config;

import java.util.Optional;

public class AuditorContextHolder {
    private static final ThreadLocal<Long> CONTEXT = new ThreadLocal<>();

    public static void setAuditorId(Long auditorId) {
        CONTEXT.set(auditorId);
    }

    public static Optional<Long> getAuditorId() {
        return Optional.ofNullable(CONTEXT.get());
    }

    public static void clear() {
        CONTEXT.remove();
    }
}