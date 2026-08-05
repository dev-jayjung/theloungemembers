package com.theloungemembers.core.type;

import com.theloungemembers.core.common.type.BaseCodeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceStatus implements BaseCodeEnum {
    IN_SERVICE("1", "사용"),
    STOPPED("0", "미사용");

    private final String code;
    private final String description;

    public static ServiceStatus of(String code) {
        for (ServiceStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }

        return STOPPED;
    }
}