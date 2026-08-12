package com.theloungemembers.web.api.dto;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUsageLogResponse {

    private Long uid;

    private String accountId;

    private String apiCode;

    private String transactionId;

    private String result;

    private String argument;

    private String response;

    private String ipAddress;

    private OffsetDateTime regDate;

}
