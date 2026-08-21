package com.theloungemembers.core.admin;

import java.util.List;

import com.theloungemembers.core.common.dto.BaseResult;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminMenuResult extends BaseResult {
    private Long uid;

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

    private Boolean isBookmark;
}