package com.theloungemembers.core.api;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUsageLogResult {

    private Integer uid;

    private String accountId;

    private String apiCode;

    private String transactionId;

    private String result;

    private String argument;

    private String response;

    private String ipAddress;

    private OffsetDateTime regDate;

}