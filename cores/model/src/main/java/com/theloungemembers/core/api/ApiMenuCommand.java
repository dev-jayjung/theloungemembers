package com.theloungemembers.core.api;

import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiMenuCommand {
    private Integer uid;

    private String code;

    private String name;

    private String displayOrdinal;

    private ServiceStatus onService;

    private String memo;
}