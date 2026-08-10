package com.theloungemembers.core.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResult {
    private TokenResult tokenResult;
    private String sessionId;
}