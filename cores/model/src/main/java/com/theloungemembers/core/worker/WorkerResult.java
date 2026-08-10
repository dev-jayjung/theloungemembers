package com.theloungemembers.core.worker;

import java.time.OffsetDateTime;

import com.theloungemembers.core.common.dto.BaseResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerResult extends BaseResult {
    private Integer uid;

    private String workerId;

    private String workerName;

    private String password;

    private OffsetDateTime passwordResetDate;

    private String passwordUpload;

    private String phoneNum;

    private String emailAddress;

    private String defaultMenuCode;

    private String menuAuth;

    private String serviceOperator;

    private Integer workerMaxIssueCount;

    private String saleChannel;

    private String autoLogin;

    private String allowOnlyOneLogin;

    private String memo;

    private Short menuAllAuth;
}