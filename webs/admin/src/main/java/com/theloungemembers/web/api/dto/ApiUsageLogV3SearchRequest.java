package com.theloungemembers.web.api.dto;

import com.theloungemembers.core.common.dto.PageRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUsageLogV3SearchRequest extends PageRequest {

    private String categoryMajor;

    private String categoryMiddle;

    private String result;

    private String referenceKey;

}
