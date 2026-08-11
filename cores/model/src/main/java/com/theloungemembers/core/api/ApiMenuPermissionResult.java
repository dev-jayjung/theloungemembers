package com.theloungemembers.core.api;

import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiMenuPermissionResult {
    private Long uid;

    private String accountId;

    private String apiCode;

    private String userData;

    private ServiceStatus onService;

    private ApiMenuGroupResult group;
}