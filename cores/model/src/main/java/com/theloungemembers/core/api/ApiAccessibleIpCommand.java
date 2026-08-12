package com.theloungemembers.core.api;

import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiAccessibleIpCommand {

    private Integer uid;

    private String ipAddress;

    private ServiceStatus onService;

    private String memo;

}
