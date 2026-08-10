package com.theloungemembers.web.common.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.exception.CommonErrorCode;
import com.theloungemembers.core.helper.JsonMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final JsonMapperHelper jsonMapperHelper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        String requestedWith = request.getHeader("X-Requested-With");

        // AJAX / REST API 요청인 경우 -> 401 Unauthorized JSON 응답
        if ("XMLHttpRequest".equals(requestedWith) || (acceptHeader != null && acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            ResponseEntity<ApiResponse<Void>> res = ResponseUtil.fail(CommonErrorCode.UNAUTHORIZED, "인증이 필요합니다.");
            response.getWriter().write(jsonMapperHelper.writeValueAsString(res));

            return;
        }

        // 일반 타임리프 화면 요청(GET HTML)인 경우 -> 로그인 페이지로 리다이렉트
        String requestURI = request.getRequestURI();

        // 가려던 원본 URL이 있다면 redirectUrl 파라미터로 붙여서 이동
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }

        String loginUrl = "/login?redirectUrl=" + URLEncoder.encode(requestURI, "UTF-8");

        response.sendRedirect(loginUrl);
    }
}