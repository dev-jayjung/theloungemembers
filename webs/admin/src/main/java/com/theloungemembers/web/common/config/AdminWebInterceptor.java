package com.theloungemembers.web.common.config;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AdminWebInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // HTTP Method 검사
        String httpMethod = request.getMethod();
        if (!("GET".equalsIgnoreCase(httpMethod) || "POST".equalsIgnoreCase(httpMethod))) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return false;
        }

        // URI 및 Ajax 체크
        String uri = request.getRequestURI();
        boolean ajaxFlag = "XMLHttpRequest".equals(request.getHeader("x-requested-with"));

        log.debug("====== [Admin Interceptor] URI: {}, Ajax: {} ======", uri, ajaxFlag);

        // 엑셀 다운로드 플래그 처리 (AS-IS 로직 유지)
        if (!ajaxFlag && uri.toLowerCase().contains("exceldownload")) {
            // 세션에 엑셀 다운로드 상태 저장
            request.getSession().setAttribute("excelDownloading", true);
        }

        return true;
    }
}