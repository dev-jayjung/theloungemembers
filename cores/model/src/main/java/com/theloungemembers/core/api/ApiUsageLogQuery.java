package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.PageRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUsageLogQuery extends PageRequest {

    private String accountId;

    private String apiCode;

    private String result;

}