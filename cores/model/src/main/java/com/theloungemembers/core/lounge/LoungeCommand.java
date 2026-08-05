package com.theloungemembers.core.lounge;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoungeCommand {
    private Integer uid;

    private String code;

    private String serviceType;

    private String subType;

    private String companyAffiliatedCode;

    private Long cityUid;

    private Long airportUid;

    private String name;
}