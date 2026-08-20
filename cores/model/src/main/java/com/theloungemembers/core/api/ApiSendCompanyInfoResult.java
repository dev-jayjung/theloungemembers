package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.BaseResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiSendCompanyInfoResult extends BaseResult {

    private Long uid;

    private String apiAccountId;

    private String companyName;

    private String type;

    private Long loungeCouponIssueHistoryUid;

    private String useSendUrl;

    private String cancelSendUrl;

    private String method;

    private Integer cancelUse;

    private String memo;

}
