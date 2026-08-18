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
 * V3 API 이용 로그
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "api_usage_log_v3")
public class ApiUsageLogV3Entity {

    /**
     * 일련번호
     */
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

    /**
     * 결과
     */
    @Column(name = "result", nullable = false)
    private String result;

    @Column(name = "member_uid")
    private Long memberUid;

    /**
     * 트랜잭션 ID
     */
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    /**
     * 이용권 / 바우처 등을 저장
     */
    @Column(name = "reference_key")
    private String referenceKey;

    /**
     * 이용권 번호 / 바우처 번호등을 저장할때 사용
     */
    @Column(name = "reference_value")
    private String referenceValue;

    /**
     * 전달 인자
     */
    @Column(name = "argument", length = Integer.MAX_VALUE)
    private String argument;

    /**
     * 응답 전문
     */
    @Column(name = "response", length = Integer.MAX_VALUE)
    private String response;

    /**
     * 에러 코드
     */
    @Column(name = "error_code")
    private String errorCode;

    /**
     * 등록일
     */
    @Column(name = "reg_date", nullable = false)
    private OffsetDateTime regDate;

}
