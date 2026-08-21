package com.theloungemembers.web.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiMemberCreateReqeust {
    private String accountId;

    private String companyName;

    private String serviceCode;

    private String password;

    private LocalDateTime passwordResetDate;

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

    private String encInfo;

    private String encKey;

    private String encIv;

    private String memo;

    private List<String> selectedMenuCodes;

    private String content;
}