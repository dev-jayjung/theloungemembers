package com.theloungemembers.core.util;

import java.util.Collection;

import com.theloungemembers.core.exception.BusinessException;

public class AssertUtil {

    private AssertUtil() {}

    public static <T> void notNull(T obj, String message) {
        if (obj == null) {
            throw new BusinessException(message);
        }
    }

    public static <T> void notNull(T obj) {
        notNull(obj, "this argument is required; it must not be null");
    }

    public static void notBlank(String text, String message) {
        if (text == null || text.trim().isEmpty()) {
            throw new BusinessException(message);
        }
    }

    public static void notBlank(String text) {
        notBlank(text, "this argument is required; it must not be blank");
    }

    public static void notEmpty(Collection<?> col, String message) {
        if (col == null || col.isEmpty()) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(Collection<?> col) {
        notEmpty(col, "this argument is required; it must not be empty");
    }

    public static void isTrue(boolean expr, String message) {
        if (!expr) {
            throw new BusinessException(message);
        }
    }

    public static void isTrue(boolean expr) {
        isTrue(expr, "this argument is required; it must not be false");
    }

    public static void notNegative(Number number, String message) {
        if (number == null || number.doubleValue() < 0) {
            throw new BusinessException(message);
        }
    }

    public static void notNegative(Number number) {
        notNegative(number, "this argument is required; it must not be negative");
    }
}