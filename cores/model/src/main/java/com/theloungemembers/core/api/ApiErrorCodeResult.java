package com.theloungemembers.core.api;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorCodeResult {

    private Long uid;

    private String errorCode;

    private String errorMeaning;

    private String memo;

    private OffsetDateTime regDate;

}
