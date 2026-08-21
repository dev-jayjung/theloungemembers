package com.theloungemembers.web.api.dto;

import com.theloungemembers.core.common.dto.BaseResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiSendCompanyDataResponse extends BaseResult {

    private Long uid;

    private String apiAccountId;

    private String companyName;

    private String type;

    private Long loungeCouponIssueHistoryUid;

    private String couponNum;

    private Integer admissionCount;

    private Integer useType;

    private Integer sendType;

    private String jsonData;

    private String responseData;

}
