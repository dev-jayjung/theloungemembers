package com.theloungemembers.web.api.dto;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUsageLogDpTempResponse {

    private Long uid;

    private String couponNum;

    private String couponType;

    private String loungeCode;

    private String apiCode;

    private String accountId;

    private String transactionId;

    private String actionFailureCode;

    private String actionFailureReason;

    private String ipAddress;

    private OffsetDateTime logRegDate;

}
