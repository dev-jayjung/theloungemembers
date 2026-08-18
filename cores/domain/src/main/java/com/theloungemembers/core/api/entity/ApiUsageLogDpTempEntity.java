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

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "api_usage_log_dp_temp")
public class ApiUsageLogDpTempEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Long uid;

    @Column(name = "coupon_num")
    private String couponNum;

    @Column(name = "coupon_type")
    private String couponType;

    @Column(name = "lounge_code")
    private String loungeCode;

    @Column(name = "api_code")
    private String apiCode;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "action_failure_code")
    private String actionFailureCode;

    @Column(name = "action_failure_reason")
    private String actionFailureReason;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "log_reg_date")
    private OffsetDateTime logRegDate;

}
