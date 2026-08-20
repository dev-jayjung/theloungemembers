package com.theloungemembers.web.api.dto;

import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiSendCompanyInfoCreateRequest {

    private String apiAccountId;

    private String companyName;

    private String type;

    private Long loungeCouponIssueHistoryUid;

    private String useSendUrl;

    private String cancelSendUrl;

    private ServiceStatus onService;

    private String memo;

}