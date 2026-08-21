package com.theloungemembers.core.type;

import com.fasterxml.jackson.annotation.JsonValue;

public interface BaseCodeEnum {
    @JsonValue
    String getCode();

    String getDescription();
}