package com.theloungemembers.core.admin;

import com.theloungemembers.core.common.dto.PageRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminMenuBookmarkQuery extends PageRequest {
    private String workerId;

    private String menuCode;
}