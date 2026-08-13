package com.theloungemembers.web.api.dto;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorLogResponse {

    private Long uid;

    private String accountId;

    private String apiCode;

    private String transactionId;

    private String errorCode;

    private String errorMsg;

    private String errorSubCode;

    private String errorSubMsg;

    private String response;

    private String argumentGet;

    private String argumentPost;

    private String argumentUserAgent;

    private String ipAddress;

    private OffsetDateTime regDate;

}
