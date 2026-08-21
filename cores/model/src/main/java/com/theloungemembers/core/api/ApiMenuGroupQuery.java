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
public class ApiMenuGroupQuery extends PageRequest {
    private String groupCode;

    private String code;

    private String name;

    private ServiceStatus onService;
}