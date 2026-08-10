package com.theloungemembers.web.api.dto;

import com.theloungemembers.core.common.dto.BaseResult;
import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiAccessibleIpResponse extends BaseResult {

    private Integer uid;

    private String ipAddress;

    private ServiceStatus onService;

    private String memo;


}