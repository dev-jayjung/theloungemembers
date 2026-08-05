package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.PageRequest;
import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiMemberQuery extends PageRequest {
    private String serviceCode;

    private String freepassIp;

    private String allowMsgNotify;

    private ServiceStatus onService;

    private String apiMenuCode;
}