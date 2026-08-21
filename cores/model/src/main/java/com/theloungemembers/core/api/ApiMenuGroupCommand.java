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
public class ApiMenuGroupCommand {
    private String groupCode;

    private String code;

    private String name;

    private String displayOrdinal;

    private ServiceStatus onService;

    private String linkUrl;

    private String memo;
}