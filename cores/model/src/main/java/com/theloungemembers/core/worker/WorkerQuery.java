package com.theloungemembers.core.worker;

import com.theloungemembers.core.common.dto.PageRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerQuery extends PageRequest {
    private String workerId;

    private String workerName;

    private String phoneNum;

    private String emailAddress;

    private String menuAuth;

    private String serviceOperator;
}