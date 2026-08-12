package com.theloungemembers.web.api.dto;

import com.theloungemembers.core.common.dto.BaseResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiMenuSearchResponse extends BaseResult {

    private Integer uid;

    private String groupCode;

    private String code;

    private String name;

    private Integer displayOrdinal;

    private String linkUrl;

    private String memo;

    private String groupName;

}
