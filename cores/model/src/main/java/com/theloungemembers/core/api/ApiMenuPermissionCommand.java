package com.theloungemembers.core.api;

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
public class ApiMenuPermissionCommand {
    private String accountId;

    private String apiCode;

    private String userData;

    private ServiceStatus onService;
}