package com.theloungemembers.web.api.dto;

import java.util.List;

import com.theloungemembers.core.common.dto.BaseResult;
import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiMenuGroupResponse extends BaseResult {
    private Integer uid;

    private String code;

    private String name;

    private String displayOrdinal;

    private ServiceStatus onService;

    private String memo;

    private List<ApiMenuResponse> menuList;
}