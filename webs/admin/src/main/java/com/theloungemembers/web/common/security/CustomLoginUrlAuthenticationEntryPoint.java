package com.theloungemembers.web.common.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.theloungemembers.core.helper.CookieHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private final CookieHelper cookieHelper;

    public CustomLoginUrlAuthenticationEntryPoint(
        @Value("${app.security.login-url:/login}") String loginUrl, CookieHelper cookieHelper) {
        super(loginUrl);
        this.cookieHelper = cookieHelper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        
        cookieHelper.delete("accessToken");
        cookieHelper.delete("sessionId");

        // 기존 /login 리다이렉트 수행
        super.commence(request, response, authException);
    }
}