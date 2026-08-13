package com.theloungemembers.core.api;


import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorSubCodeResult {

    private Long uid;

    private String errorSubCode;

    private String errorSubMeaning;

    private String errorSubMeaningKo;

    private String memo;

    private OffsetDateTime regDate;

}
