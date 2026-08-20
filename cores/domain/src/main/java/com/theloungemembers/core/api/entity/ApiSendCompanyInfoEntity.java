package com.theloungemembers.core.api.entity;

import com.theloungemembers.core.common.entity.BaseEntity;

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
 * 통보 업체 원장
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "api_send_company_info")
public class ApiSendCompanyInfoEntity extends BaseEntity {

    /**
     * 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Long uid;

    /**
     * api 계정
     */
    @Column(name = "api_account_id", nullable = false)
    private String apiAccountId;

    /**
     * 업체명
     */
    @Column(name = "company_name", nullable = false)
    private String companyName;

    /**
     * 상품타입
     */
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * 쿠폰발행내역uid
     */
    @Column(name = "lounge_coupon_issue_history_uid", nullable = false)
    private Long loungeCouponIssueHistoryUid;

    /**
     * 사용 처리 URL
     */
    @Column(name = "use_send_url", nullable = false)
    private String useSendUrl;

    /**
     * 환불 처리 url
     */
    @Column(name = "cancel_send_url", nullable = false)
    private String cancelSendUrl;

    /**
     * 통신방식
     */
    @Column(name = "method", nullable = false)
    private String method;

    /**
     * 현장 취소 불가 업체
     */
    @Column(name = "cancel_use", nullable = false)
    private Integer cancelUse;

    /**
     * 메모
     */
    @Column(name = "memo", nullable = false)
    private String memo;

}
