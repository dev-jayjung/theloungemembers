package com.theloungemembers.core.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${spring.cloud.aws.region.static:ap-northeast-2}")
    private String region;

    @Value("${spring.cloud.aws.s3.endpoint:https://kr.object.ncpstorage.com}")
    private String endpoint;

    private AwsCredentialsProvider getCredentialsProvider() {
        return StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretKey)
        );
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(getCredentialsProvider())
                .endpointOverride(URI.create(endpoint)) // NCP 전용 엔드포인트 수동 지정
                .serviceConfiguration(S3Configuration.builder()
                    .pathStyleAccessEnabled(true) // Path Style 접근 활성화
                    .build())
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(getCredentialsProvider())
                .endpointOverride(URI.create(endpoint))
                .serviceConfiguration(S3Configuration.builder()
                    .pathStyleAccessEnabled(true) // Path Style 접근 활성화
                    .build())
                .build();
    }
}