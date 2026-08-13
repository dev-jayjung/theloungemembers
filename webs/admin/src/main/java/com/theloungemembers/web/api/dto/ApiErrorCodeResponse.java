package com.theloungemembers.web.api.dto;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorCodeResponse {

    private Long id;

    private String errorCode;

    private String errorMeaning;

    private String memo;

    private OffsetDateTime regDate;

}
