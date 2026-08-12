package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.PageRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUsageLogDpTempQuery extends PageRequest {

    private Long uid;

    private String couponNum;

    private String loungeCode;

    private String apiCode;

    private String transactionId;

}
