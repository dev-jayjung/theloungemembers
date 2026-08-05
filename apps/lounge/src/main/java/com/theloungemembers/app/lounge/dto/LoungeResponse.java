package com.theloungemembers.app.lounge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoungeResponse {
    private Long uid;

    private String code;

    private String serviceType;

    private String subType;

    private String companyAffiliatedCode;

    private Long cityUid;

    private Long airportUid;

    private String name;
}