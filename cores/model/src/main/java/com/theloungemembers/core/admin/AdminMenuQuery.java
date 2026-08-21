package com.theloungemembers.core.admin;

import com.theloungemembers.core.common.dto.PageRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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