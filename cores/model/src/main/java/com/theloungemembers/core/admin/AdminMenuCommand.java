package com.theloungemembers.core.admin;

import com.theloungemembers.core.type.ServiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMenuCommand {
    private Long uid;

    private String menuCode;

    private String linkUrl;

    private String title;

    private String color;

    private String topMargin;

    private Integer displayOrdinal;

    private ServiceStatus onService;
}