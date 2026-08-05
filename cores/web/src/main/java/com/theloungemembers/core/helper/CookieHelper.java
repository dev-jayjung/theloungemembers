package com.theloungemembers.core.helper;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieHelper {

    public Optional<Cookie> get(String name) {
        if (name == null) {
            return Optional.empty();
        }

        return getRequest()
                .map(HttpServletRequest::getCookies)
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equals(name))
                        .findFirst()
                );
    }

    public void add(String name, String value, Duration maxAge) {
        if (name == null || value == null) {
            return;
        }

        getResponse().ifPresent(response -> {
            ResponseCookie cookie = createCookieBuilder(name, value)
                    .maxAge(maxAge)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        });
    }

    public void delete(String name) {
        if (name == null) {
            return;
        }

        getResponse().ifPresent(response -> {
            ResponseCookie cookie = createCookieBuilder(name, "")
                    .maxAge(0)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        });
    }

    private ResponseCookie.ResponseCookieBuilder createCookieBuilder(String name, String value) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None");
    }

    private Optional<HttpServletRequest> getRequest() {
        return getRequestAttributes()
                .map(ServletRequestAttributes::getRequest);
    }

    private Optional<HttpServletResponse> getResponse() {
        return getRequestAttributes()
                .map(ServletRequestAttributes::getResponse);
    }

    private Optional<ServletRequestAttributes> getRequestAttributes() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletAttributes) {
            return Optional.of(servletAttributes);
        }
        return Optional.empty();
    }
}