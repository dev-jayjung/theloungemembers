package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.PageRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUsageLogV3Query extends PageRequest {

    private String categoryMajor;

    private String categoryMiddle;

    private String result;

    private String referenceKey;

}
