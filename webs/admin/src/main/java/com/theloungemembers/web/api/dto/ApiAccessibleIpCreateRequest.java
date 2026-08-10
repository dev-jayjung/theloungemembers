package com.theloungemembers.web.api.dto;

import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiAccessibleIpCreateRequest {

    private String ipAddress;

    private ServiceStatus onService;

    private String memo;


}