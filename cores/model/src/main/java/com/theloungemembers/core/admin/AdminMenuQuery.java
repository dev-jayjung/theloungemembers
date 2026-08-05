package com.theloungemembers.core.admin;

import com.theloungemembers.core.common.dto.PageRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMenuQuery extends PageRequest {
    private String menuCode;

    private String linkUrl;

    private String title;

    private String color;

    private String topMargin;

    private Integer displayOrdinal;

    private Integer bookmarkDisplayOrdinal;

    private String workerId;

    private boolean isAdmin;
}