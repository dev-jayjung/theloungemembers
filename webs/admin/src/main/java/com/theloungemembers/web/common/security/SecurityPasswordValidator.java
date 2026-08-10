package com.theloungemembers.web.common.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.theloungemembers.core.security.PasswordValidator;
import com.theloungemembers.web.common.util.PasswordUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityPasswordValidator implements PasswordValidator {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }

        // 이미 BCrypt로 전환된 경우 (BCrypt 포맷 검증)
        if (PasswordUtil.isBcrypt(encodedPassword)) {
            return passwordEncoder.matches(rawPassword, encodedPassword);
        }

        // 레거시 SHA-256 비교
        String sha256Hex = PasswordUtil.encryptSha256(rawPassword.toString());

        return sha256Hex.equalsIgnoreCase(encodedPassword);
    }

    @Override
    public boolean isLegacyPassword(String encodedPassword) {
        return PasswordUtil.isLegacyPassword(encodedPassword);
    }

}