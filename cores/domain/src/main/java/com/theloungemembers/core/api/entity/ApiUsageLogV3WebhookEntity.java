package com.theloungemembers.core.api.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * V3 WEBHOOK 로그
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "api_usage_log_v3_webhook")
public class ApiUsageLogV3WebhookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Long uid;

    /**
     * 카테고리 대분류
     */
    @Column(name = "category_major", nullable = false)
    private String categoryMajor;

    /**
     * 카테고리 중분류
     */
    @Column(name = "category_middle", nullable = false)
    private String categoryMiddle;

    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * 응답 전문
     */
    @Column(name = "response")
    private String response;

    @Column(name = "request")
    private String request;

    /**
     * 등록일
     */
    @Column(name = "reg_date", nullable = false)
    private OffsetDateTime regDate;

}
