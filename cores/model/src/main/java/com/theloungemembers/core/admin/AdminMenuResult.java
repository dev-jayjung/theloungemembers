package com.theloungemembers.core.admin;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMenuResult {
    private Integer uid;

    private String menuCode;

    private String parentCode;

    private String linkUrl;

    private String title;

    private String color;

    private String topMargin;

    private Integer displayOrdinal;

    private Integer bookmarkDisplayOrdinal;

    private List<AdminMenuResult> subMenuList;

    private String mainTitle;

    private String subTitle;
}