package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.BaseResult;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiSendCompanyDataResult extends BaseResult {

    private Long uid;

    private String apiAccountId;

    private String companyName;

    private String type;

    private Long loungeCouponIssueHistoryUid;

    private String couponNum;

    private Integer admissionCount;

    private Integer useType;

    private Integer sendType;

    private Integer sendCount;

    private OffsetDateTime sendDate;

    private String sendUrl;

    private String jsonData;

    private String responseData;

    private String responseCode;

    private String responseMsg;

}
