package com.theloungemembers.core.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientFactory {

    @Bean
    RestClient restClient() {
        return RestClient.builder()
                .requestFactory(clientHttpRequestFactory())
                // .defaultHeader("Authorization", "Bearer token_value") // 필요시 공통 헤더
                .build();
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) Duration.ofSeconds(5).toMillis()); // 연결 최대 5초 대기
        factory.setReadTimeout((int) Duration.ofSeconds(10).toMillis());   // 응답 최대 10초 대기

        return factory;
    }
}