package com.theloungemembers.web.api.dto;

import com.theloungemembers.core.common.dto.PageRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorLogSearchRequest extends PageRequest {

    private String accountId;

    private String apiCode;

}
