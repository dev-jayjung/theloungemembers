package com.theloungemembers.core.api;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUsageLogV3Result {

    private Long uid;

    private String categoryMajor;

    private String categoryMiddle;

    private String result;

    private Long memberUid;

    private String transactionId;

    private String referenceKey;

    private String referenceValue;

    private String argument;

    private String response;

    private String errorCode;

    private OffsetDateTime regDate;

}
