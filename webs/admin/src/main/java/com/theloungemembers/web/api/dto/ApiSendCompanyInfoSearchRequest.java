package com.theloungemembers.web.api.dto;

import com.theloungemembers.core.common.dto.PageRequest;
import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiSendCompanyInfoSearchRequest extends PageRequest {

    private String type;

    private ServiceStatus onService;

}
