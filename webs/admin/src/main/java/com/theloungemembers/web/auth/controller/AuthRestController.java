package com.theloungemembers.web.auth.controller;

import java.time.Duration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theloungemembers.core.auth.AuthService;
import com.theloungemembers.core.auth.LoginResult;
import com.theloungemembers.core.auth.TokenResult;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.CookieHelper;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;
import com.theloungemembers.web.auth.dto.LoginReqeust;
import com.theloungemembers.web.auth.dto.TokenResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;
    private final ModelMapperHelper modelMapperHelper;
    private final CookieHelper cookieHelper;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginReqeust req) {
        LoginResult result = authService.login(req.getWorkerId(), req.getPassword());
        TokenResult tokenResult = result.getTokenResult();
        Duration expireDuration = Duration.ofSeconds(tokenResult.getExpiresIn());

        cookieHelper.add("accessToken", tokenResult.getAccessToken(), expireDuration);
        cookieHelper.add("sessionId", result.getSessionId(), expireDuration);

        return ResponseUtil.success(modelMapperHelper.map(tokenResult, TokenResponse.class));
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        cookieHelper.get("sessionId").ifPresent(cookie -> {
            authService.logout(cookie.getValue());
            cookieHelper.delete("sessionId");
        });

        cookieHelper.delete("accessToken");

        return ResponseUtil.success();
    }
}