package com.theloungemembers.core.api.entity;

import org.springframework.data.annotation.CreatedDate;

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
@Table(name = "api_usage_log")
public class ApiUsageLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Integer uid;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "api_code")
    private String apiCode;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "result")
    private String result;

    @Column(name = "argument")
    private String argument;

    @Column(name = "response")
    private String response;

    @Column(name = "ip_address")
    private String ipAddress;

    @CreatedDate
    @Column(name = "reg_date")
    private OffsetDateTime regDate;

}
