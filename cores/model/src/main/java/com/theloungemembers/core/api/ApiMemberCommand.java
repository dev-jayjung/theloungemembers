package com.theloungemembers.core.api;

import java.time.OffsetDateTime;
import java.util.List;

import com.theloungemembers.core.annotation.S3File;
import com.theloungemembers.core.helper.S3FilePath;
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
public class ApiMemberCommand {
    private String accountId;

    private String companyName;

    private String serviceCode;

    private String password;

    private OffsetDateTime passwordResetDate;

    private String freepassIp;

    private String allowMsgNotify;

    private String loungeCode;

    private String loungeName;

    private Long loungeUid;

    private ServiceStatus onService;

    private String userName;

    private String phoneNum;

    private String emailAddress;

    private Short isEncrypted;

    private String encInfo;

    private String encKey;

    private String encIv;

    private String encData;

    private String memo;

    private List<String> selectedMenuCodes;

    @S3File(path = S3FilePath.EDITOR)
    private String content;
}