package com.theloungemembers.core.common.dto;

import java.time.OffsetDateTime;

import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseResult {
    private Long sysRegNo;

    private Long sysUpdNo;

    private OffsetDateTime regDate;

    private OffsetDateTime updateDate;

    private ServiceStatus onService;
}