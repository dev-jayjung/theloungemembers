package com.theloungemembers.core.admin;

import com.theloungemembers.core.common.dto.BaseResult;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminMenuBookmarkResult extends BaseResult {
    private Long uid;

    private String workerId;

    private String menuCode;
}