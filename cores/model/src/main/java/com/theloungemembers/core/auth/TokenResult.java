package com.theloungemembers.core.auth;

import lombok.Getter;
import lombok.Setter;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TokenResult {
    private String accessToken;

    private String refreshToken;

    private Long expiresIn;

    private String tokenType;
}