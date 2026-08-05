package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.PageRequest;
import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiMenuGroupQuery extends PageRequest {
    private String groupCode;

    private String code;

    private String name;

    private ServiceStatus onService;
}