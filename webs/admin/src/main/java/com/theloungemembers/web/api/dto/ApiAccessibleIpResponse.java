package com.theloungemembers.web.api.dto;

import com.theloungemembers.core.common.dto.BaseResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiAccessibleIpResponse extends BaseResult {

    private Integer uid;

    private String ipAddress;

    private String memo;


}