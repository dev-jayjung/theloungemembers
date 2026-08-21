package com.theloungemembers.web.api.dto;

import com.theloungemembers.core.common.dto.PageRequest;
import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiMemberSearchReqeust extends PageRequest {
    private String serviceCode;

    private String freepassIp;

    private String allowMsgNotify;

    private ServiceStatus onService;

    private String apiMenuCode;
}