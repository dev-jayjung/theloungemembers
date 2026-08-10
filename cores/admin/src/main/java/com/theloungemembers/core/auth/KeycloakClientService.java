package com.theloungemembers.core.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.theloungemembers.core.helper.RestClientHelper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakClientService {

    private final RestClientHelper restClientHelper;

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    /**
     * Keycloak 서비스 토큰(Service Account Token) 발급
     */
    public TokenResult getServiceAccountToken() {
        String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token", serverUrl, realm);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);

        return restClientHelper.postForm(tokenUrl, formData, TokenResult.class);
    }
}