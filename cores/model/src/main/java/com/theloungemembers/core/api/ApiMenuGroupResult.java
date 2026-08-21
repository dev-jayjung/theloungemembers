package com.theloungemembers.core.api;

import java.util.List;

import com.theloungemembers.core.common.dto.BaseResult;
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
public class ApiMenuGroupResult extends BaseResult {
    private Long uid;

    private String code;

    private String name;

    private String displayOrdinal;

    private ServiceStatus onService;

    private String memo;

    private List<ApiMenuResult> menuList;
}