package com.theloungemembers.web.api.dto;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUsageLogV3WebhookResponse {

    private Long uid;

    private String categoryMajor;

    private String categoryMiddle;

    private String transactionId;

    private String request;

    private String response;

    private OffsetDateTime regDate;

}
