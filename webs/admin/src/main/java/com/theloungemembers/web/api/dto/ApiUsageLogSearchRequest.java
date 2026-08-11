package com.theloungemembers.web.api.dto;

import com.theloungemembers.core.common.dto.PageRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUsageLogSearchRequest extends PageRequest {

    private String accountId;

    private String apiCode;

    private String result;

}