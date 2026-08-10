package com.theloungemembers.web.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.theloungemembers.core.exception.BusinessException;
import com.theloungemembers.core.exception.CommonErrorCode;

public class PasswordUtil {

    private PasswordUtil() {}

    /**
     * 비밀번호가 BCrypt 포맷($2a$, $2b$, $2y$)인지 확인
     */
    public static boolean isBcrypt(String encodedPassword) {
        if (encodedPassword == null) {
            return false;
        }

        return encodedPassword.startsWith("$2a$") ||
               encodedPassword.startsWith("$2b$") ||
               encodedPassword.startsWith("$2y$");
    }

    /**
     * 비밀번호가 레거시(SHA-256 등) 포맷인지 확인
     */
    public static boolean isLegacyPassword(String encodedPassword) {
        return !isBcrypt(encodedPassword);
    }

    /**
     * 평문 비밀번호를 SHA-256 Hex 문자열로 변환 (레거시 검증용)
     */
    public static String encryptSha256(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawPassword.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException(CommonErrorCode.INTERNAL_SERVER_ERROR, "SHA-256 algorithm not found");
        }
    }
}