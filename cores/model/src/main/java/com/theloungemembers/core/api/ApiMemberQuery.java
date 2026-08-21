package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.PageRequest;
import com.theloungemembers.core.type.ServiceStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiMemberQuery extends PageRequest {
    private String serviceCode;

    private String freepassIp;

    private String allowMsgNotify;

    private ServiceStatus onService;

    private String apiMenuCode;
}