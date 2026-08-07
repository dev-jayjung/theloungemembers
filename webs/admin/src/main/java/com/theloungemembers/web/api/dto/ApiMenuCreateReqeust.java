package com.theloungemembers.web.api.dto;

import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiMenuCreateReqeust {

    private String groupCode;

    private String code;

    private String name;

    private Integer displayOrdinal;

    private ServiceStatus onService;

    private String linkUrl;

    private String memo;

}