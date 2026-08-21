package com.theloungemembers.core.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class S3FileInfo {
    private String s3Key; // S3 경로+파일명

    private String originalName; // 원본 파일명

    private String url; // S3 URL

    private Long size; // 파일 사이즈

    private String contentType; // 파일 MIME 타입
}