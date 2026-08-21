package com.theloungemembers.web.admin.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminMenuResponse {
    private Long uid;

    private Integer level;

    private String parentCode;

    private String menuCode;

    private String authCode;

    private String tabCode;

    private String tabName;

    private String tabTooltip;

    private Integer displayOrdinal;

    private String displayInMenu;

    private String commonWorkDomain;

    private String defaultPermission;

    private String linkUrl;

    private String title;

    private String title2;

    private String color;

    private String topMargin;

    private String helpDisplay;

    private String helpTitle;

    private Integer helpContentType;

    private String helpContent;

    private String memo;

    private Integer bookmarkDisplayOrdinal;

    private List<AdminMenuResponse> subMenuList;

    private String mainTitle;

    private String subTitle;

    private Boolean isBookmark;
}