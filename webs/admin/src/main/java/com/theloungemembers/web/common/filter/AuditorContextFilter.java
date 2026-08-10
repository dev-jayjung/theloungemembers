package com.theloungemembers.web.common.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.theloungemembers.core.common.config.AuditorContextHolder;
import com.theloungemembers.web.common.util.SecurityUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuditorContextFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // /api/ 로 시작하는 요청이 아닐 경우 스킵
        return !request.getRequestURI().startsWith("/api/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String workerId = SecurityUtil.getWorkerId();
            if (workerId != null) {
                // TODO 추후 등록자 / 수정자 컬럼 생길시 세팅
//                AuditorContextHolder.setAuditorId(workerId);
            }
        } catch (Exception e) {
            // 비인증 요청(로그인, 정적 파일 등) 시 예외가 발생할 수 있으므로 무시
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            AuditorContextHolder.clear();
        }
    }
}