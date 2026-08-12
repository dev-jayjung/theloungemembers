package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.BaseResult;
import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiAccessibleIpResult extends BaseResult {

    private Long uid;

    private String ipAddress;

    private ServiceStatus onService;

    private String memo;

}
