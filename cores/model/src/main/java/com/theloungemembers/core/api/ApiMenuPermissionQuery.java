package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.PageRequest;
import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiMenuPermissionQuery extends PageRequest {
    private String accountId;

    private String apiCode;

    private String userData;

    private ServiceStatus onService;
}