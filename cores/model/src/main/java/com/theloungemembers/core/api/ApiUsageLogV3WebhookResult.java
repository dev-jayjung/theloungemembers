package com.theloungemembers.core.api;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUsageLogV3WebhookResult {

    private Long id;

    private String categoryMajor;

    private String categoryMiddle;

    private String transactionId;

    private String response;

    private String request;

    private OffsetDateTime regDate;

}
