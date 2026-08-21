package com.theloungemembers.core.admin;

import com.theloungemembers.core.type.ServiceStatus;

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
public class AdminMenuBookmarkCommand {
    private String workerId;

    private String menuCode;

    private ServiceStatus onService;
}