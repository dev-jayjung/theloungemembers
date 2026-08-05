package com.theloungemembers.web.main.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMenuResponse {
    private Integer uid;

    private String menuCode;

    private String linkUrl;

    private String title;

    private String color;

    private String topMargin;

    private Integer displayOrdinal;

    private Integer bookmarkDisplayOrdinal;

    private List<AdminMenuResponse> subMenuList;
}
