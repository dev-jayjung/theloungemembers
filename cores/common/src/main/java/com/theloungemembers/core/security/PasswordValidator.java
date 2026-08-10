package com.theloungemembers.core.security;

/**
 * 비밀번호 암호화 및 검증을 위한 공통 인터페이스
 * (구현체는 Spring Security 의존성이 있는 web/api 모듈에 위치)
 */
public interface PasswordValidator {

    /**
     * 입력된 원문 비밀번호와 DB에 저장된 암호화된 비밀번호 비교
     * @param rawPassword 사용자 입력 비밀번호 (평문)
     * @param encodedPassword DB에 저장된 암호화 비밀번호
     * @return 일치 여부
     */
    boolean matches(String rawPassword, String encodedPassword);

    /**
     * 비밀번호 암호화 (회원가입 / 비밀번호 변경 시 사용)
     * @param rawPassword 사용자 입력 비밀번호 (평문)
     * @return 암호화된 비밀번호
     */
    String encode(String rawPassword);

    /**
     * AS-IS 패스워드 형식인지 체크 (SHA-256)
     * @return 일치 여부
     */
    boolean isLegacyPassword(String encodedPassword);
}