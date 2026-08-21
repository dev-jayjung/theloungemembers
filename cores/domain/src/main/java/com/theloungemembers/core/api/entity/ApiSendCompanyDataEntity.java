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
 * 업체 통보 데이터
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "api_send_company_data")
public class ApiSendCompanyDataEntity {

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
     * 쿠폰번호
     */
    @Column(name = "coupon_num", nullable = false)
    private String couponNum;

    /**
     * 입장인원
     */
    @Column(name = "admission_count", nullable = false)
    private Integer admissionCount;

    /**
     * 사용유무
     */
    @Column(name = "use_type", nullable = false)
    private Integer useType;

    /**
     * 전송유무(0:대기,1:성공,2:실패,3:5번 실패로 1시간에 한번 요청)
     */
    @Column(name = "send_type", nullable = false)
    private Integer sendType;

    /**
     * 전송 요청 횟수
     */
    @Column(name = "send_count", nullable = false)
    private Integer sendCount;

    /**
     * 전송일자
     */
    @Column(name = "send_date")
    private OffsetDateTime sendDate;

    /**
     * 전송 URL
     */
    @Column(name = "send_url")
    private String sendUrl;

    /**
     * json 전송데이타
     */
    @Column(name = "json_data")
    private String jsonData;

    /**
     * 응답JSON
     */
    @Column(name = "response_data")
    private String responseData;

    /**
     * 응답코드
     */
    @Column(name = "response_code")
    private String responseCode;

    /**
     * 응답메시지
     */
    @Column(name = "response_msg")
    private String responseMsg;

    /**
     * 입력일자
     */
    @Column(name = "reg_date", nullable = false)
    private OffsetDateTime regDate;

    /**
     * 수정일자
     */
    @Column(name = "update_date", nullable = false)
    private OffsetDateTime updateDate;


}