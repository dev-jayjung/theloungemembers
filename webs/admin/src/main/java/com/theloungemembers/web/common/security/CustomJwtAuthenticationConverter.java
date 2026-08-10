package com.theloungemembers.web.common.security;

import java.util.Collections;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.theloungemembers.core.exception.BusinessException;
import com.theloungemembers.core.exception.CommonErrorCode;
import com.theloungemembers.core.helper.CookieHelper;
import com.theloungemembers.core.worker.WorkerResult;
import com.theloungemembers.core.worker.WorkerService;
import com.theloungemembers.core.worker.WorkerSessionService;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final WorkerService workerSerivce;
    private final WorkerSessionService workerSessionSerivce;
    private final CookieHelper cookieHelper;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        String sessionId = cookieHelper.get("sessionId")
                .map(Cookie::getValue)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.UNAUTHORIZED, "세션 정보(sessionId) 쿠키가 존재하지 않습니다."));

        String workerId = workerSessionSerivce.getWorkerId(sessionId)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.UNAUTHORIZED, "만료되거나 유효하지 않은 세션입니다. 다시 로그인해 주세요."));

        // DB에서 회원 정보 조회 (이름, 권한 등)
        // (성능 향상이 필요하다면 Redis 캐시나 Caffein Cache를 적용하기 좋은 위치입니다)
        WorkerResult worker = workerSerivce.getByWorkerId(workerId);

        // [추후 확장] DB 권한 정보를 GrantedAuthority 리스트로 변환
        // List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + worker.getRole()));
        List<GrantedAuthority> authorities = Collections.emptyList(); // 현재는 빈 권한 목록

        // Custom Principal 객체 생성 (아이디, 이름, 권한)
        WorkerPrincipal principal = new WorkerPrincipal(
                worker.getWorkerId(),
                worker.getWorkerName(),
                authorities
        );

        // SecurityContext에 저장될 객체 생성하여 리턴
        return new UsernamePasswordAuthenticationToken(principal, jwt, authorities);
    }
}