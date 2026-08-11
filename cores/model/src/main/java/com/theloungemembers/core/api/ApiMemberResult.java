package com.theloungemembers.core.api;

import java.time.OffsetDateTime;
import java.util.List;

import com.theloungemembers.core.common.dto.BaseResult;
import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiMemberResult extends BaseResult {
    private Long uid;

    private String accountId;

    private String companyName;

    private String serviceCode;

    private String password;

    private OffsetDateTime passwordResetDate;

    private String freepassIp;

    private String allowMsgNotify;

    private String loungeCode;

    private String loungeName;

    private Integer loungeUid;

    private ServiceStatus onService;

    private String userName;

    private String phoneNum;

    private String emailAddress;

    private Short isEncrypted;

    private String encData;

    private String memo;

    private List<String> allowedMenuCodes;

    private List<ApiMenuGroupResult> menuGroupList;
}