package com.theloungemembers.core.api;

import java.util.List;

import com.theloungemembers.core.common.dto.BaseResult;
import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiMenuGroupResult extends BaseResult {
    private Long uid;

    private String code;

    private String name;

    private String displayOrdinal;

    private ServiceStatus onService;

    private String memo;

    private List<ApiMenuResult> menuList;
}